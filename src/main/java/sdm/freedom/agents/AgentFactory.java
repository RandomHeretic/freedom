package sdm.freedom.agents;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class AgentFactory {

    private static final Map<String, Supplier<AbstractAgent>> AGENT_REGISTRY = new HashMap<>();

    static {
        AGENT_REGISTRY.put("Player", HumanAgent::new);
        //further agent concepts:
        //AGENT_REGISTRY.put("Random", () -> RandomAgent::new);
        //AGENT_REGISTRY.put("AI", () -> AIAgent::new);
    }

    public static AbstractAgent create(String type){
        throw new java.lang.UnsupportedOperationException("Not yet implemented");
    }

    public static Set<String> availableAgents() {
        return AGENT_REGISTRY.keySet();
    }
}

