package com.github.krashwani.assitflow.controller;

import com.github.krashwani.assitflow.dto.PaginatedResponseDTO;
import com.github.krashwani.assitflow.dto.SupportTicketDTO;
import com.github.krashwani.assitflow.dto.TicketFilterRequestDTO;
import com.github.krashwani.assitflow.exception.apiError.BadRequestException;
import com.github.krashwani.assitflow.payload.ApiResponse;
import com.github.krashwani.assitflow.service.SupportTicketService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class SupportTicketController {
    private final SupportTicketService supportTicketService;

    public SupportTicketController(SupportTicketService supportTicketService) {
        this.supportTicketService = supportTicketService;
    }

    @PostMapping
    ApiResponse<String> createTicket(@RequestBody @Valid SupportTicketDTO supportTicketDTO){
        String ticketId = supportTicketService.createTicket(supportTicketDTO);
        return ApiResponse.success(String.format("Ticket with id %s created successfully!",ticketId));
    }

    @GetMapping("/{ticketId}")
    ApiResponse<SupportTicketDTO> getCustomerById(@PathVariable String ticketId){
        SupportTicketDTO supportTicketDTO = supportTicketService.getTicketById(ticketId).orElseThrow(
                ()-> new BadRequestException(String.format("Ticket with id %s is not present.",ticketId))
        );
        return ApiResponse.success(supportTicketDTO,"Ticket is fetched successfully!");
    }

    @GetMapping
    ApiResponse<PaginatedResponseDTO<SupportTicketDTO>> getAllTickets(@Valid @RequestBody TicketFilterRequestDTO ticketFilterRequestDTO){
        PaginatedResponseDTO<SupportTicketDTO> allTickets = supportTicketService.getAllTickets(ticketFilterRequestDTO);
        return ApiResponse.success(allTickets,"Tickets are fetched successfully!");
    }
}
