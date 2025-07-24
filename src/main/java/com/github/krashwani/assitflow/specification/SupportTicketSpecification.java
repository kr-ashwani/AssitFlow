package com.github.krashwani.assitflow.specification;

import com.github.krashwani.assitflow.domain.enums.TicketPriority;
import com.github.krashwani.assitflow.domain.enums.TicketStatus;
import com.github.krashwani.assitflow.dto.TicketFilterRequestDTO;
import com.github.krashwani.assitflow.domain.model.SupportTicket;
import com.github.krashwani.assitflow.dto.enums.TicketDTOPriority;
import com.github.krashwani.assitflow.dto.enums.TicketDTOStatus;
import org.springframework.data.jpa.domain.Specification;

public class SupportTicketSpecification {

    private static Specification<SupportTicket> combine(Specification<SupportTicket> base, Specification<SupportTicket> addition) {
        return (base == null) ? addition : base.and(addition);
    }

    private static Specification<SupportTicket> hasStatus(TicketStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    private static Specification<SupportTicket> hasPriority(TicketPriority priority) {
        return (root, query, cb) -> cb.equal(root.get("priority"), priority);
    }

    public static Specification<SupportTicket> build(TicketFilterRequestDTO filter) {
        Specification<SupportTicket> spec = null;

        if (filter.getStatus() != null) {
            TicketStatus status = mapStatus(filter.getStatus());
            spec = combine(spec, hasStatus(status));
        }

        if (filter.getPriority() != null) {
            TicketPriority priority = mapPriority(filter.getPriority());
            spec = combine(spec, hasPriority(priority));
        }
        return spec;
    }

    private static TicketStatus mapStatus(TicketDTOStatus dtoStatus) {
        return switch (dtoStatus) {
            case OPEN -> TicketStatus.OPEN;
            case PROGRESS -> TicketStatus.PROGRESS;
            case RESOLVED -> TicketStatus.RESOLVED;
            case CLOSED -> TicketStatus.CLOSED;
        };
    }

    private static TicketPriority mapPriority(TicketDTOPriority dtoPriority) {
        return TicketPriority.valueOf(dtoPriority.name());
    }
}
