package com.github.krashwani.assitflow.dto;

import com.github.krashwani.assitflow.annotation.validation.ValidEnum;
import com.github.krashwani.assitflow.dto.enums.TicketDTOPriority;
import com.github.krashwani.assitflow.dto.enums.TicketDTOStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketFilterRequestDTO {

    @Min(value = 0, message = "Page number must be 0 or greater")
    private int page = 0;

    @Min(value = 1, message = "Page size must be at least 1")
    private int size = 10;

    @ValidEnum(enumClass = TicketDTOStatus.class,includeNull = true)
    private TicketDTOStatus status;

    @ValidEnum(enumClass = TicketDTOPriority.class,includeNull = true)
    private TicketDTOPriority priority;

    @Pattern(regexp = "createdAt|updatedAt|priority|status|title",
            message = "Invalid sort field")
    private String sortBy = "createdAt";

    @ValidEnum(enumClass = Sort.Direction.class, includeNull = true)
    private Sort.Direction direction = Sort.Direction.DESC;
}
