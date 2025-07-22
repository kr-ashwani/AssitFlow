package com.github.krashwani.assitflow.dto;

import com.github.krashwani.assitflow.annotation.validation.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupportTicketDTO {
    public enum PRIORITY{
        LOW,MEDIUM,HIGH
    }
    @NotBlank(message = "Ticket title cannot be null/blank")
    private String title;
    @NotBlank(message = "Ticket description cannot be null/blank")
    private String description;
    @NotEmpty(message = "Ticket Tags cannot be empty")
    private Set<@NotBlank(message = "Ticket Tag cannot be null/blank") String> tags;
    @ValidEnum(enumClass = PRIORITY.class, message = "Ticket priority type is invalid")
    private PRIORITY priority;
    @ValidEnum(enumClass = TicketStatusDTO.STATUS.class, message = "Ticket priority type is invalid")
    private TicketStatusDTO.STATUS status = TicketStatusDTO.STATUS.OPEN;
    @NotBlank(message = "Ticket customerId cannot be null/blank")
    private String customerId;
}