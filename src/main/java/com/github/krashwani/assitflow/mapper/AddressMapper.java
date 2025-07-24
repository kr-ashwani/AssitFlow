package com.github.krashwani.assitflow.mapper;

import com.github.krashwani.assitflow.annotation.mapper.IgnoreAuditFields;
import com.github.krashwani.assitflow.config.MapperDefaultConfig;
import com.github.krashwani.assitflow.dto.AddressDTO;
import com.github.krashwani.assitflow.domain.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperDefaultConfig.class)
public interface AddressMapper {
    @Mapping(target = "current",constant = "true")
    @Mapping(target = "deleted",constant = "false")
    @Mapping(target = "id", ignore = true)
    @IgnoreAuditFields
    Address toEntity(AddressDTO dto);

    AddressDTO toDto(Address address);
}
