package sdm.freedom.agents;

import sdm.freedom.Board;
import sdm.freedom.Move;

public abstract class AbstractAgent implements FreedomAgent{
    protected final  String AGENT_NAME;

    protected AbstractAgent(String agentName) {
        AGENT_NAME = agentName;
    }

    public String getAgentName(){
        return AGENT_NAME;
    }

    public abstract Move selectNextMove(Board b, Move[] successorMoves);
}
