package sdm.freedom.agents;

import sdm.freedom.Board;
import sdm.freedom.Move;

public class HumanAgent extends AbstractAgent {

    protected HumanAgent() {
        super("Human Player");
    }

    @Override
    public Move selectNextMove(Board b, Move[] successorMoves) {

        throw new UnsupportedOperationException("Not yet implemented");

    }
}
