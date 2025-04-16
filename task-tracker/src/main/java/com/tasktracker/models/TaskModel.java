package com.tasktracker.models;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class TaskModel extends PanacheEntity {

    public String title;
    public String description;
    public boolean completed;

    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public UserModel user;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}