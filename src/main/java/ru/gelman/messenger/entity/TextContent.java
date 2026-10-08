package ru.gelman.messenger.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "text_content")
public class TextContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "index")
    private int index;
    @Column(name = "content")
    private String content;
}
