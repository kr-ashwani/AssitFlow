package com.github.krashwani.assitflow.repository;

import com.github.krashwani.assitflow.entity.SupportTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportTicketRepository extends JpaRepository<SupportTicket,String> {

    @Override
    @EntityGraph(attributePaths = {"tags"})
    Page<SupportTicket> findAll(Pageable pageable);
}
