package sdm.freedom.agents;

import sdm.freedom.Move;
import sdm.freedom.State;

public abstract class AbstractAgent implements FreedomAgent{
    protected final  String AGENT_NAME;
    protected final int PLAYER_NUMBER;

    protected AbstractAgent(String agentName, int playerNumber) {
        AGENT_NAME = agentName;
        PLAYER_NUMBER = playerNumber;
    }

    public String getAgentName(){
        return AGENT_NAME;
    }

    public abstract Move selectNextMove(State s);
}
