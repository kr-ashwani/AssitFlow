package com.github.krashwani.assitflow.mapper;

import com.github.krashwani.assitflow.dto.AgentRequestDTO;
import com.github.krashwani.assitflow.dto.AgentResponseDTO;
import com.github.krashwani.assitflow.dto.SupportTicketDTO;
import com.github.krashwani.assitflow.entity.Agent;
import com.github.krashwani.assitflow.entity.SupportTicket;
import com.github.krashwani.assitflow.entity.TicketAssignment;
import com.github.krashwani.assitflow.entity.TicketAssignmentId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {SupportTicketMapper.class})
public interface AgentMapper {

    @Mapping(target = "assignedTicketCount", expression = "java(agent.getAssignedTickets().size())")
    @Mapping(target = "assignedTickets", expression = "java(mapTickets(agent))")
    AgentResponseDTO toResponseDto(Agent agent);

    @Mapping(target = "assignedTickets", expression = "java(mapAssignments(agentRequestDTO))")
    Agent toEntity(AgentRequestDTO agentRequestDTO);

    default List<SupportTicketDTO> mapTickets(Agent agent) {
        return agent.getAssignedTickets().stream()
                .map(TicketAssignment::getTicket)
                .filter(Objects::nonNull)
                .map(this::mapTicket)
                .toList();
    }

    default Set<TicketAssignment> mapAssignments(AgentRequestDTO dto) {
        if (dto.getAssignedTicketIds() == null) return Set.of();
        Set<TicketAssignment> assignments = new HashSet<>();
        for (String ticketId : dto.getAssignedTicketIds()) {
            SupportTicket ticket = new SupportTicket();
            ticket.setId(ticketId);

            TicketAssignment assignment = new TicketAssignment();
            assignment.setTicket(ticket);
            assignment.setAssignedAt(LocalDateTime.now());
            assignment.setPrimaryHandler(false);
            assignment.setTicketAssignmentId(new TicketAssignmentId(null, ticketId));

            assignments.add(assignment);
        }
        return assignments;
    }


    SupportTicketDTO mapTicket(SupportTicket ticket);
}


