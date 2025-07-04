package com.github.krashwani.assitflow.mapper;

import com.github.krashwani.assitflow.annotation.mapper.IgnoreAuditFields;
import com.github.krashwani.assitflow.config.MapperDefaultConfig;
import com.github.krashwani.assitflow.dto.CustomerDTO;
import com.github.krashwani.assitflow.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(config = MapperDefaultConfig.class, uses = AddressMapper.class)
public interface CustomerMapper {

    @IgnoreAuditFields
    Customer toEntity(CustomerDTO source);
    CustomerDTO toDto(Customer source);
}
