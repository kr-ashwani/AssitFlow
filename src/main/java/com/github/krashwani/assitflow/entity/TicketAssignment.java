package com.github.krashwani.assitflow.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "t_ticket_assignment"
)
public class TicketAssignment extends Auditable {

    @EmbeddedId
    private TicketAssignmentId ticketAssignmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("agentId")
    @JoinColumn(name = "ticket_assignment_agent_id",nullable = false)
    private Agent agent;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ticketId")
    @JoinColumn(name = "ticket_assignment_ticket_id",nullable = false)
    private SupportTicket ticket;

    @CreatedDate
    @Column(name = "ticket_assignment_assigned_at", nullable = false)
    private LocalDateTime assignedAt;
    @Column(name = "ticket_assignment_primary_handler", nullable = false)
    private boolean primaryHandler;
}
