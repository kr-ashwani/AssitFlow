package com.github.krashwani.assitflow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AgentRequestDTO extends UserDTO {

    @NotBlank(message = "Customer phone cannot be null/blank")
    @Pattern(regexp = "^(\\+91[\\-\\s]?)?[6-9]\\d{9}$", message = "Invalid phone number")
    private String phone;
    private Set<String> skills = new HashSet<>();
    private List<String> assignedTicketIds = new ArrayList<>();
}

