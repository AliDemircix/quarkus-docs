package com.tasktracker.models.embeddables;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

@Embeddable
public class AuditInfo {
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    @Transient
    public void markCreated() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @Transient
    public void markUpdated() {
        this.updatedAt = LocalDateTime.now();
    }
}