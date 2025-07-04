package com.github.krashwani.assitflow.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class  CustomerDTO {
    private String id;
    @NotBlank(message = "Customer name cannot be null/blank")
    private String name;
    @NotBlank(message = "Customer email cannot be null/blank")
    @Email
    private String email;
    @NotBlank(message = "Customer phone cannot be null/blank")
    @Pattern(regexp = "^(\\+91[\\-\\s]?)?[6-9]\\d{9}$", message = "Invalid phone number")
    private String phone;
    @NotNull(message = "Customer address cannot be null")
    @Valid
    private AddressDTO address;
}
