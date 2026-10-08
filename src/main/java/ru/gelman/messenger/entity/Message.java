package ru.gelman.messenger.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "message_id")
    private List<TextContent> textContents;
}
