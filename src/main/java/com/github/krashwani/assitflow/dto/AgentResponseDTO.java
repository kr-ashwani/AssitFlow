package com.github.krashwani.assitflow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentResponseDTO {
    @NotBlank(message = "Agent id cannot be null/blank")
    private String id;
    @NotBlank(message = "Agent name cannot be null/blank")
    private String name;
    @NotBlank(message = "Agent email cannot be null/blank")
    @Email
    private String email;

    private Set<String> skills = new HashSet<String>();
}
