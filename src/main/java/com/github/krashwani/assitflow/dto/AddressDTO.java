package com.github.krashwani.assitflow.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    @NotBlank(message = "street cannot be blank/null")
    private String street;
    @NotBlank(message = "city cannot be blank/null")
    private String city;
    @NotBlank(message = "state cannot be blank/null")
    private String state;
    @NotBlank(message = "country cannot be blank/null")
    private String country;
    @NotNull(message = "zipcode cannot be null")
    @Min(value = 100000, message = "Zipcode must be a 6-digit number")
    @Max(value = 999999, message = "Zipcode must be a 6-digit number")
    private Integer zipCode;
}
