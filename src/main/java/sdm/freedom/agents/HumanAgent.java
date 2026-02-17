package sdm.freedom.agents;

import sdm.freedom.Move;
import sdm.freedom.State;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class HumanAgent extends AbstractAgent implements InputListenerAgent {

    protected HumanAgent(int playerNumber) {
        super("Human Player", playerNumber);
    }

    private CompletableFuture<Move> selectedMove;

    private Move[] successors;

    @Override
    public CompletableFuture<Move> selectNextMove(State state) {
        successors = state.getLegalSuccessors();
        selectedMove = new CompletableFuture<>();
        return selectedMove;
    }

    public void onUserMove(Move move) {
        if (selectedMove != null && !selectedMove.isDone())
            if(Arrays.asList(successors).contains(move)){
                selectedMove.complete(move);
            }
    }
}
