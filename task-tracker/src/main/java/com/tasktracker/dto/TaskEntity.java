package com.tasktracker.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class TaskEntity extends PanacheEntity {
    public String title;
    public String description;
    public boolean completed;
}