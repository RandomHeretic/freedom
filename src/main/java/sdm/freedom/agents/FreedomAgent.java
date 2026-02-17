package sdm.freedom.agents;

import sdm.freedom.Move;
import sdm.freedom.State;

import java.util.concurrent.CompletableFuture;

public interface FreedomAgent {
    String getAgentName();

    CompletableFuture<Move> selectNextMove(State s);
}
