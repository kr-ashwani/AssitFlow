package com.github.krashwani.assitflow.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AgentResponseDTO extends UserDTO {
    private Set<String> skills = new HashSet<String>();
}
