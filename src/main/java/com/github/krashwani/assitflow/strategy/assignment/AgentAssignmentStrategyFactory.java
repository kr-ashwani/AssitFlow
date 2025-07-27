package com.github.krashwani.assitflow.strategy.assignment;


import com.github.krashwani.assitflow.strategy.assignment.enums.AgentAssignmentStrategyType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class AgentAssignmentStrategyFactory {

    private final Map<AgentAssignmentStrategyType, AgentAssignmentStrategy> strategyMap;

    public AgentAssignmentStrategyFactory(List<AgentAssignmentStrategy> strategies) {
        this.strategyMap = new EnumMap<>(AgentAssignmentStrategyType.class);
        for (AgentAssignmentStrategy strategy : strategies) {
            strategyMap.put(strategy.getStrategyType(), strategy);
        }
    }

    public AgentAssignmentStrategy getStrategy(AgentAssignmentStrategyType type) {
        AgentAssignmentStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported assignment strategy: " + type);
        }
        return strategy;
    }
}