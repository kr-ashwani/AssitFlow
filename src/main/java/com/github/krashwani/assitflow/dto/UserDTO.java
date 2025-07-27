package com.github.krashwani.assitflow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    @NotBlank(message = "name cannot be null/blank")
    private String name;
    @NotBlank(message = "email cannot be null/blank")
    @Email
    private String email;
}
