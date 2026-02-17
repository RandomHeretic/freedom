import org.junit.Test;
import sdm.freedom.Board;
import sdm.freedom.Move;
import sdm.freedom.State;
import sdm.freedom.agents.AbstractAgent;
import sdm.freedom.agents.AgentFactory;
import sdm.freedom.agents.HumanAgent;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FreedomAgentTests {

    @Test
    public void verifyAllAgentsExist(){
        for(String agentName : AgentFactory.availableAgents()){
            assert AgentFactory.create(agentName, 1) != null;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionForUnknownAgentType() {
        AgentFactory.create("unknownAgentThatDoesNotExist", -1);
    }

    @Test
    public void verifyCorrectAgentTypeForExample(){
        //tests for the cast not breaking
        HumanAgent agent = (HumanAgent) AgentFactory.create("Player", 1);
    }

    @Test
    public void verifyValidMoveFromRandomAgent(){
        AbstractAgent agent = AgentFactory.create("Random", 2);
        Board board = new Board(3);
        State state = new State(board);
        state.applyMove(new Move(1, 1), 1);
        List<Move> successors = Arrays.asList(state.getLegalSuccessors());

        for(int attempt = 0; attempt < 10; attempt++){
            CompletableFuture<Move> nextMove = agent.selectNextMove(state);

            Move move = nextMove.join(); //wait
            assert successors.contains(move);
        }
    }

    @Test
    public void verifyValidMoveFromAIAgent(){
        AbstractAgent agent = AgentFactory.create("AI", 2);
        Board board = new Board(3);
        State state = new State(board);
        state.applyMove(new Move(1, 1), 1);
        List<Move> successors = Arrays.asList(state.getLegalSuccessors());

        CompletableFuture<Move> nextMove = agent.selectNextMove(state);

        Move move = nextMove.join(); //wait
        assert successors.contains(move);

    }

    @Test
    public void verifyAITakesWinningMove() {
        AbstractAgent agent = AgentFactory.create("AI", 1);
        Board board = new Board(new int[][] {
                new int[] {1, 1, 1, 2},
                new int[] {1, 2, 1, 2},
                new int[] {1, 2, 2, 2},
                new int[] {0, 0, 0, 1}
        });
        State state = new State(board);
        state.applyMove(new Move(3, 1), 2);
        CompletableFuture<Move> nextMove = agent.selectNextMove(state);

        Move move = nextMove.join(); //wait
        assert new Move(3, 0).equals(move);
    }

    @Test
    public void verifyAIAvoidsLoosingMove() {
        AbstractAgent agent = AgentFactory.create("AI", 2);
        Board board = new Board(new int[][] {
                new int[] {1, 1, 1, 2},
                new int[] {1, 2, 1, 2},
                new int[] {1, 2, 2, 2},
                new int[] {0, 0, 0, 1}
        });
        State state = new State(board);
        state.applyMove(new Move(3, 1), 1);
        CompletableFuture<Move> nextMove = agent.selectNextMove(state);

        Move move = nextMove.join(); //wait
        assert new Move(3, 0).equals(move);
    }
}
