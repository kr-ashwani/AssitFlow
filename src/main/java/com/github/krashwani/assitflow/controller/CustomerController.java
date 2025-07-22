package com.github.krashwani.assitflow.controller;

import com.github.krashwani.assitflow.dto.AddressDTO;
import com.github.krashwani.assitflow.dto.CustomerDTO;
import com.github.krashwani.assitflow.exception.apiError.BadRequestException;
import com.github.krashwani.assitflow.payload.ApiResponse;
import com.github.krashwani.assitflow.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    ApiResponse<String> createCustomer(@RequestBody @Valid CustomerDTO customerDTO){
        String customerId = customerService.createCustomer(customerDTO);
        return ApiResponse.success(String.format("Customer with id '%s' created successfully!",customerId));
    }

    @GetMapping("/{customerId}")
    ApiResponse<CustomerDTO> getCustomerById(@PathVariable String customerId){
        CustomerDTO customerDTO = customerService.getCustomerById(customerId).orElseThrow(
                ()-> new BadRequestException(String.format("Customer id '%s' is not registered.",customerId))
        );
        return ApiResponse.success(customerDTO,"Customer is fetched successfully!");
    }

    @PostMapping("/{customerId}/address")
    ApiResponse<String> updateCustomerAddress(@PathVariable String customerId,@RequestBody @Valid AddressDTO addressDTO){
        customerService.updateCustomerAddress(customerId,addressDTO);
        return ApiResponse.success("Address updated successfully!");
    }
}
