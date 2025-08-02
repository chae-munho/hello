package com.example.hello.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    public Board() {}

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }

    // ✅ update용 setter
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
}