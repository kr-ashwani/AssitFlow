package com.github.krashwani.assitflow.service.strategy.assignment.impl;

import com.github.krashwani.assitflow.domain.model.Agent;
import com.github.krashwani.assitflow.domain.model.SupportTicket;
import com.github.krashwani.assitflow.domain.model.TicketAssignmentId;
import com.github.krashwani.assitflow.repository.AgentRepository;
import com.github.krashwani.assitflow.repository.SupportTicketRepository;
import com.github.krashwani.assitflow.repository.TicketAssignmentRepository;
import com.github.krashwani.assitflow.service.strategy.assignment.AgentAssignmentStrategy;
import com.github.krashwani.assitflow.service.strategy.assignment.enums.AgentAssignmentStrategyType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@RequiredArgsConstructor
public class LeastLoadedAgentAssignmentStrategy implements AgentAssignmentStrategy {

    private final AgentRepository agentRepository;
    private final SupportTicketRepository supportTicketRepository;
    private final TicketAssignmentRepository ticketAssignmentRepository;

    @Override
    public String findAgentIdForTicket(String ticketId) {
        SupportTicket ticket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        return agentRepository.findAll().stream()
                .filter(agent -> agent.getSkills().containsAll(ticket.getTags()))
                .filter(agent -> ticketAssignmentRepository.findById(new TicketAssignmentId(agent.getId(), ticketId)).isEmpty())
                .min(Comparator.comparingInt(agent -> agent.getAssignedTickets().size()))
                .map(Agent::getId)
                .orElseThrow(() -> new IllegalStateException("No agent found"));
    }

    @Override
    public AgentAssignmentStrategyType getStrategyType() {
        return AgentAssignmentStrategyType.LEAST_LOADED;
    }
}

