package com.github.krashwani.assitflow.specification;

import com.github.krashwani.assitflow.domain.model.Agent;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class AgentSpecification {

    public static Specification<Agent> hasAllSkills(Set<String> requiredSkills) {
        return (root, query, cb) -> {
            // Join skills as element collection
            Join<Agent, String> skillsJoin = root.joinSet("skills");

            // Filter only skills present in required set
            Predicate skillInRequiredSet = skillsJoin.in(requiredSkills);

            // Group by agent and count matches
            query.groupBy(root.get("id"));

            // Having count = requiredSkills.size()
            query.having(cb.equal(cb.count(skillsJoin), requiredSkills.size()));

            return skillInRequiredSet;
        };
    }
}

