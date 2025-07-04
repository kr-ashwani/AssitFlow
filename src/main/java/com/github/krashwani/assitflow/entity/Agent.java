package com.github.krashwani.assitflow.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "t_agent"
)
public class Agent extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "agent_id", nullable = false)
    private String id;
    @Column(name = "agent_name", nullable = false)
    private String name;
    @Column(name = "agent_email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "agent")
    @ToString.Exclude
    private Set<TicketAssignment> assignedTickets = new HashSet<TicketAssignment>();
    @ElementCollection
    @CollectionTable(
            name = "t_agent_skills",
            joinColumns = @JoinColumn(name = "agent_id")
    )
    private Set<String> skills;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Agent agent = (Agent) o;
        return getId() != null && Objects.equals(getId(), agent.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
