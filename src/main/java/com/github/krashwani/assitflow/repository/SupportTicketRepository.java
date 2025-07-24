package com.github.krashwani.assitflow.repository;

import com.github.krashwani.assitflow.domain.model.SupportTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SupportTicketRepository extends JpaRepository<SupportTicket,String>, JpaSpecificationExecutor<SupportTicket> {

    @Override
    @EntityGraph(attributePaths = {"tags"})
    Page<SupportTicket> findAll(Specification<SupportTicket> spec,Pageable pageable);
}
