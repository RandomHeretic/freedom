package sdm.freedom.agents;

import sdm.freedom.Move;
import sdm.freedom.State;

public interface FreedomAgent {
    String getAgentName();

    Move selectNextMove(State s);
}
