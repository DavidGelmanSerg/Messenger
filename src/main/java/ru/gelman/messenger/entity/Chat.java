package ru.gelman.messenger.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import ru.gelman.messenger.enums.ChatType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type")
    private ChatType type;
    @Column(name = "created_at")
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
}
