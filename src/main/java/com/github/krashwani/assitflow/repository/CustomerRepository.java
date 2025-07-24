package com.github.krashwani.assitflow.repository;

import com.github.krashwani.assitflow.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,String> {
        Customer findByAddress_Id(Long id);
}
