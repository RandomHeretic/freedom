package sdm.freedom.agents;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class AgentFactory {

    private static final Map<String, Function<Integer, AbstractAgent>> AGENT_REGISTRY = new HashMap<>();

    static {
        AGENT_REGISTRY.put("Player", HumanAgent::new);
        AGENT_REGISTRY.put("Random", RandomAgent::new);
        AGENT_REGISTRY.put("AI", MinimaxAgent::new);
    }

    public static AbstractAgent create(String type, int playerNumber){
        Function<Integer, AbstractAgent> supplier = AGENT_REGISTRY.get(type);
        if (supplier == null) throw new IllegalArgumentException("Unknown agent type: " + type);
        return supplier.apply(playerNumber);
    }

    public static Set<String> availableAgents() {
        return AGENT_REGISTRY.keySet();
    }
}

