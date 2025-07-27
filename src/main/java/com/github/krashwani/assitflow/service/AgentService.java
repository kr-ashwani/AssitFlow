package com.github.krashwani.assitflow.service;

import com.github.krashwani.assitflow.dto.AgentRequestDTO;
import com.github.krashwani.assitflow.dto.AgentResponseDTO;
import com.github.krashwani.assitflow.dto.SupportTicketDTO;

import java.util.List;
import java.util.Optional;

public interface AgentService {
    public String createAgent(AgentRequestDTO agentRequestDTO);
    public Optional<AgentResponseDTO> getAgentById(String agentId);
    public List<SupportTicketDTO> getAllTicketsAssignedToAgent(String agentId);
    public void assignTicketToAgent(String agentId, String ticketId);
    public String autoAssignTicketToAgent(String ticketId);
    public List<AgentResponseDTO> findAgentsWithAnyMatchingSkills(List<String> skills);
    public List<AgentResponseDTO> findAgentsWithAllMatchingSkills( List<String> skills);
}
