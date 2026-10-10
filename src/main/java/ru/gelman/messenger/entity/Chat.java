package ru.gelman.messenger.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import ru.gelman.messenger.enums.ChatType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "chats")
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private ChatType type;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    @ToString.Exclude
    private List<Message> messages;
    @ManyToMany
    @JoinTable(
            name = "chat_users",
            joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    private List<User> users;

    public static Chat personal(User initiator, User recipient) {
        return Chat.builder()
                .type(ChatType.PERSONAL)
                .users(List.of(initiator, recipient))
                .build();
    }

    public static Chat group(String name, String description, List<User> users) {
        return Chat.builder()
                .type(ChatType.GROUP)
                .name(name)
                .description(description)
                .users(users)
                .build();
    }
}
