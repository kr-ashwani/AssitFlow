package com.github.krashwani.assitflow.service;

import com.github.krashwani.assitflow.dto.AgentRequestDTO;
import com.github.krashwani.assitflow.dto.AgentResponseDTO;
import com.github.krashwani.assitflow.dto.SupportTicketDTO;

import java.util.List;

public interface AgentService {
    public String createAgent(AgentRequestDTO agentRequestDTO);
    public AgentResponseDTO getAllTicketsAssignedToAgent(String agentId);
    public void assignTicketToAgent(String agentId, String ticketId);
    public String autoAssignTicketToAgent(String ticketId);
}
