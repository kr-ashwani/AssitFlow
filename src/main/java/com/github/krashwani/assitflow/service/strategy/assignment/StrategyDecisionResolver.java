package com.github.krashwani.assitflow.service.strategy.assignment;

import com.github.krashwani.assitflow.domain.enums.TicketPriority;
import com.github.krashwani.assitflow.domain.model.SupportTicket;
import com.github.krashwani.assitflow.service.strategy.assignment.enums.AgentAssignmentStrategyType;
import org.springframework.stereotype.Component;

@Component
public class StrategyDecisionResolver {

    public AgentAssignmentStrategyType resolveStrategyType(SupportTicket ticket) {
        // Prioritize skill-based assignment for high-priority tickets
        if (ticket.getPriority() == TicketPriority.HIGH) {
            return AgentAssignmentStrategyType.SKILL_BASED;
        }

        // Use round-robin for low-priority tickets
        if (ticket.getPriority() == TicketPriority.LOW) {
            return AgentAssignmentStrategyType.ROUND_ROBIN;
        }

        // Fallback to least-loaded for others
        return AgentAssignmentStrategyType.LEAST_LOADED;
    }
}

