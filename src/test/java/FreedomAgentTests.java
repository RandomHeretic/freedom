import org.junit.Test;
import sdm.freedom.Board;
import sdm.freedom.Move;
import sdm.freedom.State;
import sdm.freedom.agents.AbstractAgent;
import sdm.freedom.agents.AgentFactory;
import sdm.freedom.agents.HumanAgent;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

public class FreedomAgentTests {

    @Test
    public void verifyAllAgentsExist(){
        for(String agentName : AgentFactory.availableAgents()){
            assert AgentFactory.create(agentName) != null;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionForUnknownAgentType() {
        AgentFactory.create("unknownAgentThatDoesNotExist");
    }

    @Test
    public void verifyCorrectAgentTypeForExample(){
        //tests for the cast not breaking
        HumanAgent agent = (HumanAgent) AgentFactory.create("Player");
    }

    @Test
    public void verifyValidMoveFromPlayerAgent(){
        AbstractAgent agent = AgentFactory.create("Player");
        Board board = new Board(3);
        State state = new State(board);
        state.applyMove(new Move(1, 1), 0);
        List<Move> successors = Arrays.asList(state.getLegalSuccessors());

        for(int i = 0; i < successors.size(); i++){
            System.setIn(new ByteArrayInputStream(String.valueOf(i).getBytes()));
            Move nextMove = agent.selectNextMove(state.giveBoard(), successors.toArray(new Move[0]));

            assert successors.contains(nextMove);
        }
        //reset
        System.setIn(System.in);
    }

}
