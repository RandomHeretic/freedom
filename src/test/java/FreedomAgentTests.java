import org.junit.Test;
import sdm.freedom.Board;
import sdm.freedom.Move;
import sdm.freedom.State;
import sdm.freedom.agents.AbstractAgent;
import sdm.freedom.agents.AgentFactory;
import sdm.freedom.agents.HumanAgent;
import java.util.Arrays;

public class FreedomAgentTests {

    @Test
    public void verifyAllAgentsExist(){
        for(String agentName : AgentFactory.availableAgents()){
            assert AgentFactory.create(agentName) != null;
        }
    }

    @Test(expected = Exception.class)
    public void throwsExceptionForUnknownAgentType() {
        AgentFactory.create("unknownAgentThatDoesNotExist");
    }

    @Test
    public void verifyCorrectAgentTypeForExample(){
        HumanAgent agent = (HumanAgent) AgentFactory.create("Player");
    }

    @Test
    public void verifyValidMoveFromAgents(){
        for(String agentName : AgentFactory.availableAgents()){
            AbstractAgent agent = AgentFactory.create("Player");
            Board board = new Board(3);
            State state = new State(board);
            state.applyMove(new Move(1, 1), 0);
            assert Arrays.asList(state.getLegalSuccessors()).contains( agent.selectNextMove(state.giveBoard(), state.getLegalSuccessors()));
        }
    }

}
