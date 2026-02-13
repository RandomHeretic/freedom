package sdm.freedom.agents;

import sdm.freedom.Board;
import sdm.freedom.Move;

public interface FreedomAgent {
    String getAgentName();

    Move selectNextMove(Board b, Move[] successorMoves);
}
