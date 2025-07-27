package com.github.krashwani.assitflow.service.impl;

import com.github.krashwani.assitflow.domain.enums.UserRole;
import com.github.krashwani.assitflow.dto.AgentRequestDTO;
import com.github.krashwani.assitflow.dto.AgentResponseDTO;
import com.github.krashwani.assitflow.domain.model.Agent;
import com.github.krashwani.assitflow.domain.model.SupportTicket;
import com.github.krashwani.assitflow.domain.model.TicketAssignment;
import com.github.krashwani.assitflow.domain.model.TicketAssignmentId;
import com.github.krashwani.assitflow.dto.SupportTicketDTO;
import com.github.krashwani.assitflow.exception.domain.AgentNotFoundException;
import com.github.krashwani.assitflow.exception.domain.TicketNotFoundException;
import com.github.krashwani.assitflow.mapper.AgentMapper;
import com.github.krashwani.assitflow.mapper.SupportTicketMapper;
import com.github.krashwani.assitflow.repository.AgentRepository;
import com.github.krashwani.assitflow.repository.SupportTicketRepository;
import com.github.krashwani.assitflow.repository.TicketAssignmentRepository;
import com.github.krashwani.assitflow.service.AgentService;

import com.github.krashwani.assitflow.specification.AgentSpecification;
import com.github.krashwani.assitflow.strategy.assignment.AgentAssignmentStrategy;
import com.github.krashwani.assitflow.strategy.assignment.AgentAssignmentStrategyFactory;
import com.github.krashwani.assitflow.strategy.assignment.enums.AgentAssignmentStrategyType;
import com.github.krashwani.assitflow.strategy.assignment.StrategyDecisionResolver;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final AgentMapper agentMapper;
    private final SupportTicketMapper supportTicketMapper;
    private final TicketAssignmentRepository ticketAssignmentRepository;
    private final SupportTicketRepository supportTicketRepository;
    private final AgentAssignmentStrategyFactory strategyFactory;
    private final StrategyDecisionResolver strategyResolver;

    public AgentServiceImpl(AgentRepository agentRepository, AgentMapper agentMapper, SupportTicketMapper supportTicketMapper, TicketAssignmentRepository ticketAssignmentRepository, SupportTicketRepository supportTicketRepository, AgentAssignmentStrategyFactory strategyFactory, StrategyDecisionResolver strategyResolver) {
        this.agentRepository = agentRepository;
        this.agentMapper = agentMapper;
        this.supportTicketMapper = supportTicketMapper;
        this.ticketAssignmentRepository = ticketAssignmentRepository;
        this.supportTicketRepository = supportTicketRepository;
        this.strategyFactory = strategyFactory;
        this.strategyResolver = strategyResolver;
    }


    @Transactional
    @Override
    public String createAgent(AgentRequestDTO agentRequestDTO) {
        log.info("Creating new agent with details: {}", agentRequestDTO);

        Agent agent = agentMapper.toEntity(agentRequestDTO);
        agent.setRole(UserRole.AGENT);
        Agent savedAgent = agentRepository.save(agent);
        log.info("Agent saved with ID: {}", savedAgent.getId());

        Set<TicketAssignment> assignments = new HashSet<>();

        for (TicketAssignment assignment : agent.getAssignedTickets()) {
            assignment.setAgent(savedAgent);
            assignment.getTicketAssignmentId().setAgentId(savedAgent.getId());
            assignments.add(assignment);
        }

        if (!assignments.isEmpty()) {
            ticketAssignmentRepository.saveAll(assignments);
            log.info("Assigned {} tickets to agent {}", assignments.size(), savedAgent.getId());
        }

        return savedAgent.getId();
    }

    @Override
    public Optional<AgentResponseDTO> getAgentById(String agentId) {
        return agentRepository.findById(agentId).map(agentMapper::toResponseDto);
    }

    @Override
    public List<SupportTicketDTO> getAllTicketsAssignedToAgent(String agentId) {
        List<SupportTicket> ticketsAssignedToAgent = supportTicketRepository.findTicketsAssignedToAgent(agentId);

        return ticketsAssignedToAgent.stream()
                .map(supportTicketMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void assignTicketToAgent(String agentId, String ticketId) {
        TicketAssignmentId assignmentId = new TicketAssignmentId(agentId, ticketId);
        if (ticketAssignmentRepository.existsById(assignmentId)) {
            log.warn("Ticket {} is already assigned to agent {}", ticketId, agentId);
            return;
        }

        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException("Invalid agent ID: " + agentId));
        SupportTicket ticket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Invalid ticket ID: " + ticketId));

        TicketAssignment assignment = new TicketAssignment();
        assignment.setTicket(ticket);
        assignment.setAgent(agent);
        assignment.setAssignedAt(LocalDateTime.now());
        assignment.setPrimaryHandler(true); // move to strategy/service if needed
        assignment.setTicketAssignmentId(assignmentId);

        ticketAssignmentRepository.save(assignment);
        log.info("Assigned ticket {} to agent {}", ticketId, agentId);
    }

    @Transactional
    @Override
    public String autoAssignTicketToAgent(String ticketId) {
        log.info("Auto-assigning ticket: {}", ticketId);

        SupportTicket ticket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));

        AgentAssignmentStrategyType strategyType = strategyResolver.resolveStrategyType(ticket);
        log.info("Resolved strategy: {} for ticket {}", strategyType, ticketId);

        AgentAssignmentStrategy strategy = strategyFactory.getStrategy(strategyType);
        String agentId = strategy.findAgentIdForTicket(ticketId);

        assignTicketToAgent(agentId, ticketId);
        log.info("Ticket {} assigned to agent {} using {}", ticketId, agentId, strategyType);

        return agentId;
    }

    @Override
    public List<AgentResponseDTO> findAgentsWithAnyMatchingSkills( List<String> skills){
        return agentRepository.findAgentsWithAnyMatchingSkills(skills)
                .stream()
                .map(agentMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<AgentResponseDTO> findAgentsWithAllMatchingSkills( List<String> skills){
        Specification<Agent> allSkillAgentSpecification = AgentSpecification.hasAllSkills(new HashSet<>(skills));
        return agentRepository.findAll(allSkillAgentSpecification)
                .stream()
                .map(agentMapper::toResponseDto)
                .toList();
    }

}
