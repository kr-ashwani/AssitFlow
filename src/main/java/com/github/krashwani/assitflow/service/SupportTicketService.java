package com.github.krashwani.assitflow.service;

import com.github.krashwani.assitflow.dto.PaginatedResponseDTO;
import com.github.krashwani.assitflow.dto.SupportTicketDTO;
import com.github.krashwani.assitflow.dto.TicketFilterRequestDTO;
import com.github.krashwani.assitflow.dto.TicketStatusDTO;

import java.util.Optional;

public interface SupportTicketService {

    public String createTicket(SupportTicketDTO supportTicketDTO);
    public void updateTicketStatus(String ticketId, TicketStatusDTO ticketStatusDTO);
    public Optional<SupportTicketDTO> getTicketById(String ticketId);
    public PaginatedResponseDTO<SupportTicketDTO> getAllTickets(TicketFilterRequestDTO ticketFilterRequestDTO);
}
