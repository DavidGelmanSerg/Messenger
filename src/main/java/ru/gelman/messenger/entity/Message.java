package ru.gelman.messenger.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "messages")
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "message_id")
    private List<TextContent> textContents;

    public static Message simpleTextMessage(User sender, String content) {
        TextContent textContent = new TextContent();
        textContent.setContent(content);
        textContent.setIndex(0);
        return Message.builder().sender(sender).textContents(List.of(textContent)).build();
    }
}
