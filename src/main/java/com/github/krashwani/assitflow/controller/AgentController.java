package com.github.krashwani.assitflow.controller;

import com.github.krashwani.assitflow.dto.AgentRequestDTO;
import com.github.krashwani.assitflow.dto.AgentResponseDTO;
import com.github.krashwani.assitflow.payload.ApiResponse;
import com.github.krashwani.assitflow.service.AgentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agents")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping
    ApiResponse<String> createAgent(@RequestBody @Valid AgentRequestDTO agentRequestDTO){
        String agentId = agentService.createAgent(agentRequestDTO);
        return ApiResponse.success(String.format("Agent with id '%s' created successfully!",agentId));
    }

    @GetMapping("/{agentId}/tickets")
    ApiResponse<AgentResponseDTO> getAllAssignedTicketsToAgent(@PathVariable String agentId){
        AgentResponseDTO allTicketsAssignedToAgent = agentService.getAllTicketsAssignedToAgent(agentId);
        return ApiResponse.success(allTicketsAssignedToAgent,"All assigned tickets are fetched successfully!");
    }

    @PostMapping("/{agentId}/assign-ticket/{ticketId}")
    ApiResponse<String> assignTicketToAgent(@PathVariable String agentId,@PathVariable String ticketId){
        agentService.assignTicketToAgent(agentId,ticketId);
        return ApiResponse.success(String.format("Ticket id '%s' is successfully assigned to agent id '%s'",ticketId,agentId));
    }

    @PostMapping("/assign-ticket/{ticketId}")
    ApiResponse<String> assignTicketToQualifiedAgent(@PathVariable String ticketId){
        String agentId = agentService.autoAssignTicketToAgent(ticketId);
        return ApiResponse.success(String.format("Ticket id '%s' is successfully assigned to agent id '%s'",ticketId,agentId));
    }
}
