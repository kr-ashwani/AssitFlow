package com.github.krashwani.assitflow.service.strategy.assignment.impl;

import com.github.krashwani.assitflow.domain.model.Agent;
import com.github.krashwani.assitflow.repository.AgentRepository;
import com.github.krashwani.assitflow.service.strategy.assignment.AgentAssignmentStrategy;
import com.github.krashwani.assitflow.service.strategy.assignment.enums.AgentAssignmentStrategyType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoundRobinAgentAssignmentStrategy implements AgentAssignmentStrategy {

    private final AgentRepository agentRepository;

    private static int lastIndex = -1;

    @Override
    public String findAgentIdForTicket(String ticketId) {
        List<Agent> agents = agentRepository.findAll();
        if (agents.isEmpty()) throw new IllegalStateException("No agents available");

        lastIndex = (lastIndex + 1) % agents.size();
        return agents.get(lastIndex).getId();
    }

    @Override
    public AgentAssignmentStrategyType getStrategyType() {
        return AgentAssignmentStrategyType.ROUND_ROBIN;
    }
}

