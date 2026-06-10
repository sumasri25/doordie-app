package com.taskflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Task text cannot be blank")
    @Size(max = 500, message = "Task text must be under 500 characters")
    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private boolean completed = false;

    @Column(length = 10)
    private String priority = "none"; // none | low | med | high

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    // ─── Constructors ────────────────────────────────────────────────
    public Todo() {}

    public Todo(String text, String priority) {
        this.text = text;
        this.priority = priority != null ? priority : "none";
    }

    // ─── Getters & Setters ───────────────────────────────────────────
    public Long getId()                { return id; }
    public void setId(Long id)         { this.id = id; }

    public String getText()            { return text; }
    public void setText(String text)   { this.text = text; }

    public boolean isCompleted()              { return completed; }
    public void setCompleted(boolean c)       { this.completed = c; }

    public String getPriority()               { return priority; }
    public void setPriority(String priority)  { this.priority = priority; }

    public Instant getCreatedAt()             { return createdAt; }
    public void setCreatedAt(Instant t)       { this.createdAt = t; }
}