package com.github.krashwani.assitflow.repository;

import com.github.krashwani.assitflow.domain.model.TicketAssignment;
import com.github.krashwani.assitflow.domain.model.TicketAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketAssignmentRepository extends JpaRepository<TicketAssignment, TicketAssignmentId> {
}
