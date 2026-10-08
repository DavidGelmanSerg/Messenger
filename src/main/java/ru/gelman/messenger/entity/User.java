package ru.gelman.messenger.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringExclude;
import ru.gelman.messenger.entity.Chat;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @ManyToMany
    @JoinTable(
            name = "chat_users",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    private List<Chat> chats;
}
