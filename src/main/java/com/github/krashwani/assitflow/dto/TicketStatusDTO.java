package com.github.krashwani.assitflow.dto;

import com.github.krashwani.assitflow.annotation.validation.ValidEnum;
import com.github.krashwani.assitflow.dto.enums.TicketDTOStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketStatusDTO {
    @ValidEnum(enumClass = TicketDTOStatus.class, message = "Ticket status type is invalid")
    private TicketDTOStatus status;
}
