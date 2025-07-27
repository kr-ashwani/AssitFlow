package com.github.krashwani.assitflow.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "t_agent")
@PrimaryKeyJoinColumn(name = "agent_id")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Agent extends User{

    @Column(name = "agent_phone", nullable = false)
    private String phone;

    @OneToMany(mappedBy = "agent")
    @ToString.Exclude
    private Set<TicketAssignment> assignedTickets = new HashSet<TicketAssignment>();
    @ElementCollection
    @CollectionTable(
            name = "t_agent_skills",
            joinColumns = @JoinColumn(name = "agent_id")
    )
    private Set<String> skills = new HashSet<String>();
}
