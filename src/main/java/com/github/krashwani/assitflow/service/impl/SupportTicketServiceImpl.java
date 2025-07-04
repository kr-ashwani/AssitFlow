package com.github.krashwani.assitflow.service.impl;

import com.github.krashwani.assitflow.dto.PaginatedResponseDTO;
import com.github.krashwani.assitflow.dto.SupportTicketDTO;
import com.github.krashwani.assitflow.dto.TicketFilterRequestDTO;
import com.github.krashwani.assitflow.entity.SupportTicket;
import com.github.krashwani.assitflow.exception.apiError.BadRequestException;
import com.github.krashwani.assitflow.mapper.PaginatedResponseMapper;
import com.github.krashwani.assitflow.mapper.SupportTicketMapper;
import com.github.krashwani.assitflow.repository.CustomerRepository;
import com.github.krashwani.assitflow.repository.SupportTicketRepository;
import com.github.krashwani.assitflow.service.SupportTicketService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupportTicketServiceImpl implements SupportTicketService {
    private final SupportTicketMapper supportTicketMapper;
    private final PaginatedResponseMapper paginatedResponseMapper;
    private final SupportTicketRepository supportTicketRepository;
    private final CustomerRepository customerRepository;

    public SupportTicketServiceImpl(SupportTicketMapper supportTicketMapper, PaginatedResponseMapper paginatedResponseMapper, SupportTicketRepository supportTicketRepository, CustomerRepository customerRepository) {
        this.supportTicketMapper = supportTicketMapper;
        this.paginatedResponseMapper = paginatedResponseMapper;
        this.supportTicketRepository = supportTicketRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public String createTicket(SupportTicketDTO supportTicketDTO){
        SupportTicket supportTicket = supportTicketMapper.toEntity(supportTicketDTO);
        String customerId = supportTicket.getCustomer().getId();
        if(!customerRepository.existsById(customerId))
            throw new BadRequestException(String.format("Failed to create ticket as customer with id %s is not registered.",customerId));
        supportTicketRepository.save(supportTicket);
        return supportTicket.getId();
    }

    public Optional<SupportTicketDTO> getTicketById(String ticketId){
        Optional<SupportTicket> supportTicket = supportTicketRepository.findById(ticketId);
        return supportTicket.map(supportTicketMapper::toDto);
    }

    public PaginatedResponseDTO<SupportTicketDTO> getAllTickets(TicketFilterRequestDTO ticketFilterRequestDTO){
        Sort sort = Sort.by(new Sort.Order(ticketFilterRequestDTO.getDirection(),ticketFilterRequestDTO.getSortBy()));
        PageRequest page = PageRequest.of(ticketFilterRequestDTO.getPage(),ticketFilterRequestDTO.getSize(),sort);
        Page<SupportTicket> ticketsPage = supportTicketRepository.findAll(page);
        return paginatedResponseMapper.convertPageToResponseDTO(ticketsPage,supportTicketMapper::toDto);
    }
}
