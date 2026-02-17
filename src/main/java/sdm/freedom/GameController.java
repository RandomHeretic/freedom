package sdm.freedom;

import sdm.freedom.agents.AbstractAgent;
import sdm.freedom.agents.InputListenerAgent;

import javax.swing.*;
import java.util.concurrent.CompletableFuture;


public class GameController implements MoveInputListener {

    private static GameController instance;

    private Match match;
    private UIController uiController;
    private AbstractAgent[] agents;


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


        match.applyMove(move);

        int[] scores = match.evaluateBoard();
        boolean terminal = match.getCurrentState().isTerminal();

        // Run UI update separately, as to not break game logic by having it wait for unrelated code
        SwingUtilities.invokeLater(() -> {
            uiController.refresh(getPlayerTurn(), scores[0], scores[1]);
            uiController.repaintBoard();

            if (terminal) {
                endGame(scores);
            }
        });

        // Run next turn by itself as soon as possible. It should avoid async issues with other features (UI)
        if (!terminal) {
            CompletableFuture.runAsync(this::startTurn);
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
        if (match == null) {
            return false;
        }
        for (Move m : getLegalMoves()) {
            if (m.skipMove()) {
                return true;
            }
        }
        return false;
    }



    public int[][] getBoard() {
        return match.getCurrentState().getBoard().getBoardMatrix();
    }

    public int getPlayerTurn() {
        return match.getCurrentPlayer();
    }

    @Override
    public void onMoveSelected(Move move) {

        AbstractAgent agent = agents[match.getCurrentPlayerIdx()];

        if (agent instanceof InputListenerAgent inputAgent) {
            inputAgent.onUserMove(move);
        }
    }
}
