package com.tasktracker.models;

import com.tasktracker.models.embeddables.AuditInfo;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Embedded;
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

    // public LocalDateTime createdAt;
    // public LocalDateTime updatedAt;
    @Embedded
    public AuditInfo auditInfo = new AuditInfo();

    @ManyToOne
    @JoinColumn(name = "user_id")
    public UserModel user;

    @PrePersist
    public void onCreate() {
        auditInfo.markCreated();
    }

    @PreUpdate
    public void onUpdate() {
        auditInfo.markUpdated();
    }
}