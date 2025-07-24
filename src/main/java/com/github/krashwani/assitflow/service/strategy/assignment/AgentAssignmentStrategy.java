package com.github.krashwani.assitflow.service.strategy.assignment;

import com.github.krashwani.assitflow.service.strategy.assignment.enums.AgentAssignmentStrategyType;

public interface AgentAssignmentStrategy {
    String findAgentIdForTicket(String ticketId);
    AgentAssignmentStrategyType getStrategyType();
}

