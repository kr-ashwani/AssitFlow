package com.github.krashwani.assitflow.strategy.assignment;

import com.github.krashwani.assitflow.strategy.assignment.enums.AgentAssignmentStrategyType;

public interface AgentAssignmentStrategy {
    String findAgentIdForTicket(String ticketId);
    AgentAssignmentStrategyType getStrategyType();
}

