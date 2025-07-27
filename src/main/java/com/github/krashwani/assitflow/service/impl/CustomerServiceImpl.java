package com.github.krashwani.assitflow.service.impl;

import com.github.krashwani.assitflow.domain.enums.UserRole;
import com.github.krashwani.assitflow.dto.AddressDTO;
import com.github.krashwani.assitflow.dto.CustomerDTO;
import com.github.krashwani.assitflow.domain.model.Customer;
import com.github.krashwani.assitflow.exception.apiError.BadRequestException;
import com.github.krashwani.assitflow.exception.domain.CustomerNotFoundException;
import com.github.krashwani.assitflow.mapper.AddressMapper;
import com.github.krashwani.assitflow.mapper.CustomerMapper;
import com.github.krashwani.assitflow.repository.CustomerRepository;
import com.github.krashwani.assitflow.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    CustomerMapper customerMapper;
    CustomerRepository customerRepository;
    AddressMapper addressMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper,AddressMapper addressMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.addressMapper = addressMapper;
    }

    @Transactional
    @Override
    public String createCustomer(CustomerDTO customerDTO){
        Customer customer = customerMapper.toEntity(customerDTO);
        customer.setRole(UserRole.CUSTOMER);
        customerRepository.save(customer);
        return customer.getId();
    }

    @Transactional
    @Override
    public Optional<CustomerDTO> getCustomerById(String customerId){
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.map(customerMapper::toDto);
    }

    @Transactional
    @Override
    public void updateCustomerAddress(String customerId, AddressDTO addressDTO){
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                ()->new CustomerNotFoundException(String.format("Customer id '%s' is not registered",customerId)
        ));
        customer.getAddress().setCurrent(false);
        customer.setAddress(addressMapper.toEntity(addressDTO));
    }
}
