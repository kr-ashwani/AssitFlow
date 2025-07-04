package com.github.krashwani.assitflow.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_support_ticket")
public class SupportTicket extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ticket_id", nullable = false)
    private String id;
    @Column(name = "ticket_title", nullable = false)
    private String title;
    @Column(name = "ticket_description", nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_status", nullable = false)
    private STATUS status;
    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_priority", nullable = false)
    private PRIORITY priority;
    @ElementCollection
    @CollectionTable(
            name = "t_ticket_tags",
            joinColumns = @JoinColumn(name = "ticket_id")
    )
    @Column(name = "tag", nullable = false)
    private Set<String> tags;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_customer_id",nullable = false,referencedColumnName = "customer_id")
    @ToString.Exclude
    private Customer customer;

    @OneToMany(mappedBy = "ticket")
    @ToString.Exclude
    private Set<TicketAssignment> assignedAgents = new HashSet<TicketAssignment>();

    public enum STATUS{
        OPEN,IN_PROGRESS,RESOLVED,CLOSED
    }
    public enum PRIORITY{
        LOW,MEDIUM,HIGH
    }
}
