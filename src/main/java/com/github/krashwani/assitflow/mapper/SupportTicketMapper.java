package com.github.krashwani.assitflow.mapper;

import com.github.krashwani.assitflow.annotation.mapper.IgnoreAuditFields;
import com.github.krashwani.assitflow.config.MapperDefaultConfig;
import com.github.krashwani.assitflow.dto.SupportTicketDTO;
import com.github.krashwani.assitflow.entity.Customer;
import com.github.krashwani.assitflow.entity.SupportTicket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperDefaultConfig.class)
public interface SupportTicketMapper {

    @IgnoreAuditFields
    @Mapping(target = "customer", expression = "java(mapCustomerId(source.getCustomerId()))")
    @Mapping(target = "id" ,ignore = true)
    @Mapping(target = "assignedAgents" ,ignore = true)
    @Mapping(target = "priority" ,expression = "java(SupportTicket.PRIORITY.valueOf(source.getPriority().name()))")
    @Mapping(target = "status" ,expression = "java(SupportTicket.STATUS.valueOf(source.getStatus().name()))")
    SupportTicket toEntity(SupportTicketDTO source);
    @Mapping(target = "customerId", expression = "java(source.getCustomer().getId())")
    @Mapping(target = "priority" ,expression = "java(SupportTicketDTO.PRIORITY.valueOf(source.getPriority().name()))")
    @Mapping(target = "status" ,expression = "java(com.github.krashwani.assitflow.dto.TicketStatusDTO.STATUS.valueOf(source.getStatus().name()))")
    SupportTicketDTO toDto(SupportTicket source);

    // Helper method for mapping customerId to Customer
    default Customer mapCustomerId(String customerId) {
        if (customerId == null) return null;
        Customer customer = new Customer();
        customer.setId(customerId);
        return customer;
    }
}
