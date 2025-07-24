package com.github.krashwani.assitflow.service.strategy.assignment.impl;

import com.github.krashwani.assitflow.domain.model.Agent;
import com.github.krashwani.assitflow.domain.model.SupportTicket;
import com.github.krashwani.assitflow.repository.AgentRepository;
import com.github.krashwani.assitflow.repository.SupportTicketRepository;
import com.github.krashwani.assitflow.service.strategy.assignment.AgentAssignmentStrategy;
import com.github.krashwani.assitflow.service.strategy.assignment.enums.AgentAssignmentStrategyType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SkillBasedAgentAssignmentStrategy implements AgentAssignmentStrategy {

    private final AgentRepository agentRepository;
    private final SupportTicketRepository supportTicketRepository;

    @Override
    public String findAgentIdForTicket(String ticketId) {
        SupportTicket ticket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        return agentRepository.findAll().stream()
                .filter(agent -> agent.getSkills().containsAll(ticket.getTags()))
                .findFirst()
                .map(Agent::getId)
                .orElseThrow(() -> new IllegalStateException("No skilled agent found"));
    }

    @Override
    public AgentAssignmentStrategyType getStrategyType() {
        return AgentAssignmentStrategyType.SKILL_BASED;
    }
}

