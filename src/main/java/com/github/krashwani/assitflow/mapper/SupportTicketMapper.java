package com.github.krashwani.assitflow.mapper;

import com.github.krashwani.assitflow.annotation.mapper.IgnoreAuditFields;
import com.github.krashwani.assitflow.config.MapperDefaultConfig;
import com.github.krashwani.assitflow.domain.enums.TicketPriority;
import com.github.krashwani.assitflow.domain.enums.TicketStatus;
import com.github.krashwani.assitflow.dto.SupportTicketDTO;
import com.github.krashwani.assitflow.domain.model.Customer;
import com.github.krashwani.assitflow.domain.model.SupportTicket;
import com.github.krashwani.assitflow.dto.enums.TicketDTOPriority;
import com.github.krashwani.assitflow.dto.enums.TicketDTOStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperDefaultConfig.class)
public interface SupportTicketMapper {

    @IgnoreAuditFields
    @Mapping(target = "customer", expression = "java(mapCustomerId(source.getCustomerId()))")
    @Mapping(target = "id" ,ignore = true)
    @Mapping(target = "assignedAgents" ,ignore = true)
    @Mapping(target = "priority" ,expression = "java(mapDTOPriorityToEntity(source.getPriority()))")
    @Mapping(target = "status" ,expression = "java(mapDTOStatusToEntity(source.getStatus()))")
    SupportTicket toEntity(SupportTicketDTO source);
    @Mapping(target = "customerId", expression = "java(source.getCustomer().getId())")
    @Mapping(target = "priority" ,expression = "java(mapEntityPriorityToDTO(source.getPriority()))")
    @Mapping(target = "status" ,expression = "java(mapEntityStatusToDTO(source.getStatus()))")
    SupportTicketDTO toDto(SupportTicket source);

    // Helper method for mapping customerId to Customer
    default Customer mapCustomerId(String customerId) {
        if (customerId == null) return null;
        Customer customer = new Customer();
        customer.setId(customerId);
        return customer;
    }

    default TicketPriority mapDTOPriorityToEntity(TicketDTOPriority dtoPriority){
        return TicketPriority.valueOf(dtoPriority.name());
    }
    default TicketDTOPriority mapEntityPriorityToDTO(TicketPriority entityPriority){
        return TicketDTOPriority.valueOf(entityPriority.name());
    }
    default TicketStatus mapDTOStatusToEntity(TicketDTOStatus dtoStatus){
        return TicketStatus.valueOf(dtoStatus.name());
    }
    default TicketDTOStatus mapEntityStatusToDTO(TicketStatus entityStatus){
        return TicketDTOStatus.valueOf(entityStatus.name());
    }
}
