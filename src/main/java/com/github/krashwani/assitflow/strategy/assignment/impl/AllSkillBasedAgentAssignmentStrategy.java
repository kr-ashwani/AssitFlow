package com.github.krashwani.assitflow.strategy.assignment.impl;

import com.github.krashwani.assitflow.domain.model.SupportTicket;
import com.github.krashwani.assitflow.dto.AgentResponseDTO;
import com.github.krashwani.assitflow.exception.domain.TicketAssignmentException;
import com.github.krashwani.assitflow.exception.domain.TicketNotFoundException;
import com.github.krashwani.assitflow.repository.SupportTicketRepository;
import com.github.krashwani.assitflow.service.AgentService;
import com.github.krashwani.assitflow.strategy.assignment.AgentAssignmentStrategy;
import com.github.krashwani.assitflow.strategy.assignment.enums.AgentAssignmentStrategyType;
import org.springframework.context.annotation.Lazy;

import java.util.List;

public class AllSkillBasedAgentAssignmentStrategy implements AgentAssignmentStrategy {
    private final AgentService agentService;
    private final SupportTicketRepository supportTicketRepository;

    public AllSkillBasedAgentAssignmentStrategy(@Lazy AgentService agentService, SupportTicketRepository supportTicketRepository) {
        this.agentService = agentService;
        this.supportTicketRepository = supportTicketRepository;
    }
    @Override
    public String findAgentIdForTicket(String ticketId) {
        SupportTicket supportTicket = supportTicketRepository.findById(ticketId)
                .orElseThrow(
                        () -> new TicketNotFoundException(String.format("Ticket id '%s' is not present.", ticketId))
                );
        List<AgentResponseDTO> agentsWithAnyMatchingSkills = agentService.findAgentsWithAllMatchingSkills(supportTicket.getTags().stream().toList());
        if(agentsWithAnyMatchingSkills.isEmpty())
            throw new TicketAssignmentException(String.format("Failed to assign ticket id '%s' to any skilled agent.", ticketId));

        return agentsWithAnyMatchingSkills.getFirst().getId();
    }

    @Override
    public AgentAssignmentStrategyType getStrategyType() {
        return AgentAssignmentStrategyType.ALL_SKILL_BASED;
    }
}
