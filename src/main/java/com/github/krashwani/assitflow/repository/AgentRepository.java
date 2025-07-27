package com.github.krashwani.assitflow.repository;

import com.github.krashwani.assitflow.domain.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgentRepository extends JpaRepository<Agent,String>, JpaSpecificationExecutor<Agent> {
    @Query("SELECT a FROM Agent a JOIN a.skills s WHERE s IN :tags GROUP BY a HAVING COUNT(s) > 0")
    List<Agent> findAgentsWithAnyMatchingSkills(@Param("tags") List<String> tags);

}
