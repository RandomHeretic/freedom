package sdm.freedom.agents;

import sdm.freedom.Move;
import sdm.freedom.State;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class RandomAgent extends AbstractAgent {

    protected RandomAgent(int playerNumber) {
        super("Random Player", playerNumber);
    }

    @Override
    public CompletableFuture<Move> selectNextMove(State s) {

        Move[] successors = s.getLegalSuccessors();
        Move selectedMove = successors[new Random().nextInt(successors.length)];
        return CompletableFuture.completedFuture(selectedMove);

    }
}
