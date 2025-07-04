package com.github.krashwani.assitflow.mapper;


import com.github.krashwani.assitflow.config.MapperDefaultConfig;
import com.github.krashwani.assitflow.dto.PaginatedResponseDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(config = MapperDefaultConfig.class)
public interface PaginatedResponseMapper {

    default <T,R> PaginatedResponseDTO<R> convertPageToResponseDTO(Page<T> page, Function<T, R> mapper){

        return new PaginatedResponseDTO<R>(
                page.getContent().stream().map(mapper).collect(Collectors.toList()),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}
