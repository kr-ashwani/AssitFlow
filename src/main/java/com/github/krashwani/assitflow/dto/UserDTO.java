package com.github.krashwani.assitflow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    private String id;
    @NotBlank(message = "Customer name cannot be null/blank")
    private String name;
    @NotBlank(message = "Customer email cannot be null/blank")
    @Email
    private String email;
}
