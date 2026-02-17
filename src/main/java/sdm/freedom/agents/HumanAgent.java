package sdm.freedom.agents;

import sdm.freedom.Move;
import sdm.freedom.State;

import java.util.concurrent.CompletableFuture;

public class HumanAgent extends AbstractAgent implements InputListenerAgent {

    protected HumanAgent(int playerNumber) {
        super("Human Player", playerNumber);
    }

    private CompletableFuture<Move> selectedMove;


    @Override
    public CompletableFuture<Move> selectNextMove(State state) {
        selectedMove = new CompletableFuture<>();
        return selectedMove;
    }

    public void onUserMove(Move move) {
        if (selectedMove != null && !selectedMove.isDone())
            selectedMove.complete(move);
    }
}
