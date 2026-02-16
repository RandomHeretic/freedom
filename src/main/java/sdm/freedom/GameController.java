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
        return  match.giveCurrentState().getLegalSuccessors();
    }

    private void startTurn() {
        AbstractAgent agent = agents[match.getCurrentPlayerIdx()];

        CompletableFuture<Move> future =
                agent.selectNextMove(match.giveCurrentState());

        future.thenAccept(this::applyMove);
    }

    private void applyMove(Move move) {
        if (!match.checkValidMove(move)) throw new IllegalArgumentException("Illegal move");

        match.applyAMove(move);

        int[] scores = match.evaluateBoard();
        uiController.refresh(match.getCurrentPlayer(), scores[0], scores[1]);
        uiController.repaintBoard();

        if (match.giveCurrentState().isTerminal()) {
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

    public boolean isGameOver(){
        return gameOver;
    }

    public int[][] getBoard() {
        return match.giveCurrentState().getBoard().getBoardMatrix();
    }

    @Override
    public void onMoveSelected(Move move) {
        AbstractAgent agent = agents[match.getCurrentPlayerIdx()];

        if (agent instanceof InputListenerAgent inputAgent) {
            inputAgent.onUserMove(move);
        }
    }
}
