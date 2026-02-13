package sdm.freedom.agents;

import sdm.freedom.Board;
import sdm.freedom.Move;

public interface FreedomAgent {
    public String getAgentName();

    public Move selectNextMove(Board b, Move[] successorMoves);
}
