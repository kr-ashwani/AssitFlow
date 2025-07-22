package com.github.krashwani.assitflow.service.impl;

import com.github.krashwani.assitflow.dto.AgentRequestDTO;
import com.github.krashwani.assitflow.dto.AgentResponseDTO;
import com.github.krashwani.assitflow.entity.Agent;
import com.github.krashwani.assitflow.entity.SupportTicket;
import com.github.krashwani.assitflow.entity.TicketAssignment;
import com.github.krashwani.assitflow.entity.TicketAssignmentId;
import com.github.krashwani.assitflow.exception.apiError.BadRequestException;
import com.github.krashwani.assitflow.mapper.AgentMapper;
import com.github.krashwani.assitflow.repository.AgentRepository;
import com.github.krashwani.assitflow.repository.TicketAssignmentRepository;
import com.github.krashwani.assitflow.service.AgentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final AgentMapper agentMapper;
    private final TicketAssignmentRepository ticketAssignmentRepository;

    public AgentServiceImpl(AgentRepository agentRepository, AgentMapper agentMapper, TicketAssignmentRepository ticketAssignmentRepository) {
        this.agentRepository = agentRepository;
        this.agentMapper = agentMapper;
        this.ticketAssignmentRepository = ticketAssignmentRepository;
    }


    @Transactional
    @Override
    public String createAgent(AgentRequestDTO agentRequestDTO) {
        Agent agent = agentMapper.toEntity(agentRequestDTO);
        agentRepository.save(agent);

        for (TicketAssignment assignment : agent.getAssignedTickets()) {
            assignment.setAgent(agent);
            assignment.getTicketAssignmentId().setAgentId(agent.getId());
            ticketAssignmentRepository.save(assignment);
        }
        return agent.getId();
    }

    @Override
    public AgentResponseDTO getAllTicketsAssignedToAgent(String agentId) {
        Agent agent = agentRepository.findById(agentId).orElseThrow(
                ()-> new BadRequestException(String.format("Agent id '%s' is not registered.",agentId))
        );
        return agentMapper.toResponseDto(agent);
    }

    @Transactional
    @Override
    public void assignTicketToAgent(String agentId, String ticketId) {
        Optional<TicketAssignment> ticketAssignment = ticketAssignmentRepository
                .findById(new TicketAssignmentId(agentId,ticketId));

        // ticketId is already assigned to agentId
        if(ticketAssignment.isPresent()) return;
        SupportTicket ticket = new SupportTicket();
        ticket.setId(ticketId);
        Agent agent = new Agent();
        agent.setId(agentId);

        TicketAssignment assignment = new TicketAssignment();
        assignment.setTicket(ticket);
        assignment.setAgent(agent);
        assignment.setAssignedAt(LocalDateTime.now());
        assignment.setPrimaryHandler(true);
        assignment.setTicketAssignmentId(new TicketAssignmentId(agentId, ticketId));
        ticketAssignmentRepository.save(assignment);
    }

    @Override
    public String autoAssignTicketToAgent(String ticketId) {
        return "";
    }
}
