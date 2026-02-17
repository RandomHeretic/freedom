package sdm.freedom;

import sdm.freedom.agents.AbstractAgent;
import sdm.freedom.agents.InputListenerAgent;

import java.util.concurrent.CompletableFuture;


public class GameController implements MoveInputListener {

    private static GameController instance;

    private Match match;
    private UIController uiController;
    private AbstractAgent[] agents;
    private boolean gameOver = false;

    private GameController() {
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void initialize(int boardSize, UIController uiController, AbstractAgent[] agents) {
        this.uiController = uiController;
        this.agents = agents;
        this.match = new Match(boardSize);

        uiController.setMoveInputListener(this);
        startTurn();
    }

    public Move[] getLegalMoves() {
        if (gameOver) return new Move[0];
        return  match.getCurrentState().getLegalSuccessors();
    }

    public Move getLastMove() {
        return match.getCurrentState().getLastMove();
    }

    private void startTurn() {
        AbstractAgent agent = agents[match.getCurrentPlayerIdx()];

        CompletableFuture<Move> future =
                agent.selectNextMove(match.getCurrentState());

        future.thenAccept(this::applyMove);
    }

    private void applyMove(Move move) {
        if (!match.checkValidMove(move)) return;

        match.applyMove(move);

        int[] scores = match.evaluateBoard();
        uiController.refresh(getPlayerTurn(), scores[0], scores[1]);
        uiController.repaintBoard();

        if (match.getCurrentState().isTerminal()) {
            gameOver = true;
            endGame(scores);
        } else {
            startTurn();
        }
    }

    private void endGame(int[] scores) {
        String result =
                scores[0] > scores[1] ? "Vince il BIANCO!" :
                        scores[1] > scores[0] ? "Vince il NERO!" :
                                "PAREGGIO!";

        uiController.showGameOver(result, scores[0], scores[1]);
    }

    public boolean canSkip() {
        if (match == null || gameOver) {
            return false;
        }
        for (Move m : getLegalMoves()) {
            if (m.skipMove()) {
                return true;
            }
        }
        return false;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public int[][] getBoard() {
        return match.getCurrentState().getBoard().getBoardMatrix();
    }

    public int getPlayerTurn() {
        return match.getCurrentPlayer();
    }

    @Override
    public void onMoveSelected(Move move) {
        if(!match.checkValidMove(move)) return;
        AbstractAgent agent = agents[match.getCurrentPlayerIdx()];

        if (agent instanceof InputListenerAgent inputAgent) {
            inputAgent.onUserMove(move);
        }
    }
}
