package com.github.krashwani.assitflow.service;

import com.github.krashwani.assitflow.dto.AddressDTO;
import com.github.krashwani.assitflow.dto.CustomerDTO;
import java.util.Optional;

public interface CustomerService {

    public String createCustomer(CustomerDTO customerDTO);
    public Optional<CustomerDTO> getCustomerById(String customerId);
    public void updateCustomerAddress(String customerId, AddressDTO addressDTO);
}

