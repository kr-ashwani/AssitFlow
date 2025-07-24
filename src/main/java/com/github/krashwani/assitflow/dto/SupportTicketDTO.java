package com.github.krashwani.assitflow.dto;

import com.github.krashwani.assitflow.annotation.validation.ValidEnum;
import com.github.krashwani.assitflow.dto.enums.TicketDTOPriority;
import com.github.krashwani.assitflow.dto.enums.TicketDTOStatus;
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
    @NotBlank(message = "Ticket title cannot be null/blank")
    private String title;
    @NotBlank(message = "Ticket description cannot be null/blank")
    private String description;
    @NotEmpty(message = "Ticket Tags cannot be empty")
    private Set<@NotBlank(message = "Ticket Tag cannot be null/blank") String> tags;
    @ValidEnum(enumClass = TicketDTOPriority.class, message = "Ticket priority type is invalid")
    private TicketDTOPriority priority = TicketDTOPriority.LOW;
    @ValidEnum(enumClass = TicketDTOStatus.class, message = "Ticket priority type is invalid")
    private TicketDTOStatus status = TicketDTOStatus.OPEN;
    @NotBlank(message = "Ticket customerId cannot be null/blank")
    private String customerId;
}