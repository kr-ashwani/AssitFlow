package com.github.krashwani.assitflow.strategy.assignment;

import com.github.krashwani.assitflow.domain.enums.TicketPriority;
import com.github.krashwani.assitflow.domain.model.SupportTicket;
import com.github.krashwani.assitflow.strategy.assignment.enums.AgentAssignmentStrategyType;
import org.springframework.stereotype.Component;

@Component
public class StrategyDecisionResolver {

    public AgentAssignmentStrategyType resolveStrategyType(SupportTicket ticket) {
        // Prioritize all skill-based assignment for high-priority tickets
        if (ticket.getPriority() == TicketPriority.HIGH) {
            return AgentAssignmentStrategyType.ALL_SKILL_BASED;
        }

        // Prioritize any skill-based assignment for high-priority tickets
        if (ticket.getPriority() == TicketPriority.MEDIUM) {
            return AgentAssignmentStrategyType.ANY_SKILL_BASED;
        }

        // Prioritize any skill-based assignment for high-priority tickets
        if (ticket.getPriority() == TicketPriority.LOW) {
            return AgentAssignmentStrategyType.ANY_SKILL_BASED;
        }

        // Fallback to round-robin for others
        return AgentAssignmentStrategyType.ROUND_ROBIN;
    }
}

