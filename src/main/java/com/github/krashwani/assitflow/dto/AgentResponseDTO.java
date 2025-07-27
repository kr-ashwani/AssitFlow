package com.github.krashwani.assitflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "Agent phone cannot be null/blank")
    @Pattern(regexp = "^(\\+91[\\-\\s]?)?[6-9]\\d{9}$", message = "Invalid phone number")
    private String phone;
    private Set<String> skills = new HashSet<String>();
}
