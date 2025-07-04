package com.github.krashwani.assitflow.repository;

import com.github.krashwani.assitflow.entity.TicketAssignment;
import com.github.krashwani.assitflow.entity.TicketAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketAssignmentRepository extends JpaRepository<TicketAssignment, TicketAssignmentId> {
}
