package com.github.krashwani.assitflow.dto;

import com.github.krashwani.assitflow.annotation.validation.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketStatusDTO {
    public enum STATUS{
        OPEN,IN_PROGRESS,RESOLVED,CLOSED
    }
    @ValidEnum(enumClass = TicketStatusDTO.STATUS.class, message = "Ticket status type is invalid")
    private TicketStatusDTO.STATUS status;
}
