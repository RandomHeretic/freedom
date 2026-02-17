package sdm.freedom;

import java.util.concurrent.CompletableFuture;

import javax.swing.SwingUtilities;

import sdm.freedom.agents.AbstractAgent;
import sdm.freedom.agents.InputListenerAgent;

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

        refreshUI();
        CompletableFuture.runAsync(this::startTurn);
    }

    public Move[] getLegalMoves() {
        if (gameOver) {
            return new Move[0];
        }
        return match.getCurrentState().getLegalSuccessors();
    }

    public Move getLastMove() {
        return match.getCurrentState().getLastMove();
    }

    private void startTurn() {
        AbstractAgent agent = agents[match.getCurrentPlayerIdx()];

        CompletableFuture<Move> future
                = agent.selectNextMove(match.getCurrentState());

        future.thenAccept(this::applyMove);
    }

    private void applyMove(Move move) {
        if (!match.checkValidMove(move)) {
            return;
        }

        match.applyMove(move);

        boolean terminal = match.getCurrentState().isTerminal();

        refreshUI();

        if (terminal) {
            SwingUtilities.invokeLater(this::endGame);
        }

        // Run next turn by itself as soon as possible. It should avoid async issues with other features (UI)
        if (!terminal) {
            CompletableFuture.runAsync(this::startTurn);
        }
    }

    private void refreshUI() {
        int[] scores = match.evaluateBoard();
        SwingUtilities.invokeLater(() -> {
            uiController.refresh(getPlayerTurn(), scores[0], scores[1]);
            uiController.repaintBoard();
        });
    }

    private void endGame() {
        int[] scores = match.evaluateBoard();
        String result
                = scores[0] > scores[1] ? "Vince il BIANCO!"
                        : scores[1] > scores[0] ? "Vince il NERO!"
                                : "PAREGGIO!";

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

    public boolean isGameOver() {
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
        if (!match.checkValidMove(move)) {
            return;
        }
        AbstractAgent agent = agents[match.getCurrentPlayerIdx()];

        if (agent instanceof InputListenerAgent inputAgent) {
            inputAgent.onUserMove(move);
        }
    }
}
