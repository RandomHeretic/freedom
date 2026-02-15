package sdm.freedom.agents;

import sdm.freedom.Move;
import sdm.freedom.State;

import java.util.Random;

public class RandomAgent extends AbstractAgent {

    protected RandomAgent(int playerNumber) {
        super("Random Player", playerNumber);
    }

    @Override
    public Move selectNextMove(State s) {

        Move[] successors =s.getLegalSuccessors();
        return successors[new Random().nextInt(successors.length)];

    }
}
