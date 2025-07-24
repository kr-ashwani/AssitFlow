package com.github.krashwani.assitflow.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "t_ticket_comment"
)
public class TicketComment extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String id;

    @JoinColumn(name = "ticket_comment_message",nullable = false)
    private String message;

    @CreatedDate
    @JoinColumn(name = "ticket_comment_commented_at",nullable = false)
    private LocalDateTime commentedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_comment_ticket_id",nullable = false)
    @ToString.Exclude
    private SupportTicket ticket;

    @JoinColumn(name = "ticket_comment_attachment_url",nullable = true)
    private String attachmentUrl;
}

