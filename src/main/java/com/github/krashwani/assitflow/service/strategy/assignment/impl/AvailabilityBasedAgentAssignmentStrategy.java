package com.github.krashwani.assitflow.service.strategy.assignment.impl;

import com.github.krashwani.assitflow.domain.model.Agent;
import com.github.krashwani.assitflow.repository.AgentRepository;
import com.github.krashwani.assitflow.service.strategy.assignment.AgentAssignmentStrategy;
import com.github.krashwani.assitflow.service.strategy.assignment.enums.AgentAssignmentStrategyType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AvailabilityBasedAgentAssignmentStrategy implements AgentAssignmentStrategy {

    private final AgentRepository agentRepository;

    @Override
    public String findAgentIdForTicket(String ticketId) {
        return agentRepository.findAll().stream()
                .filter(agent -> isAgentAvailable(agent)) // Mocked logic
                .findFirst()
                .map(Agent::getId)
                .orElseThrow(() -> new IllegalStateException("No available agent"));

    }

    private boolean isAgentAvailable(Agent agent) {
        // TODO: Hook into availability service/calendar
        return true;
    }

    @Override
    public AgentAssignmentStrategyType getStrategyType() {
        return AgentAssignmentStrategyType.AVAILABILITY_BASED;
    }
}

