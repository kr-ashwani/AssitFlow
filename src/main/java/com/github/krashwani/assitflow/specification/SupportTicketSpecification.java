package com.github.krashwani.assitflow.specification;

import com.github.krashwani.assitflow.dto.TicketFilterRequestDTO;
import com.github.krashwani.assitflow.entity.SupportTicket;
import org.springframework.data.jpa.domain.Specification;

public class SupportTicketSpecification {

    private static Specification<SupportTicket> combine(Specification<SupportTicket> base, Specification<SupportTicket> addition) {
        return (base == null) ? addition : base.and(addition);
    }

    private static Specification<SupportTicket> hasStatus(SupportTicket.STATUS status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    private static Specification<SupportTicket> hasPriority(SupportTicket.PRIORITY priority) {
        return (root, query, cb) -> cb.equal(root.get("priority"), priority);
    }

    public static Specification<SupportTicket> build(TicketFilterRequestDTO filter) {
        Specification<SupportTicket> spec = null;

        if (filter.getStatus() != null) {
            SupportTicket.STATUS status = mapStatus(filter.getStatus());
            spec = combine(spec, hasStatus(status));
        }

        if (filter.getPriority() != null) {
            SupportTicket.PRIORITY priority = mapPriority(filter.getPriority());
            spec = combine(spec, hasPriority(priority));
        }
        return spec;
    }

    private static SupportTicket.STATUS mapStatus(TicketFilterRequestDTO.TicketStatus dtoStatus) {
        return switch (dtoStatus) {
            case OPEN -> SupportTicket.STATUS.OPEN;
            case PROGRESS -> SupportTicket.STATUS.IN_PROGRESS;
            case RESOLVED -> SupportTicket.STATUS.RESOLVED;
            case CLOSED -> SupportTicket.STATUS.CLOSED;
        };
    }

    private static SupportTicket.PRIORITY mapPriority(TicketFilterRequestDTO.TicketPriority dtoPriority) {
        return SupportTicket.PRIORITY.valueOf(dtoPriority.name());
    }
}
