package com.github.krashwani.assitflow.repository;

import com.github.krashwani.assitflow.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent,String> {
}
