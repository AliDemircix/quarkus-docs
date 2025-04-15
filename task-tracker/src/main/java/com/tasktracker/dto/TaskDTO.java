package com.tasktracker.dto;

import com.tasktracker.models.TaskModel;

import jakarta.validation.constraints.NotBlank;

public class TaskDTO {
    @NotBlank(message = "Title cannot be blank") // Validation annotation
    public String title;
    public String description;
    public boolean completed;

    public TaskDTO() {
        // Default constructor
    }

    public TaskDTO(String title, String description, boolean completed) {
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    // ✅ Convert DTO to Entity
    public TaskModel toEntity() {
        TaskModel task = new TaskModel();
        task.title = this.title;
        task.description = this.description;
        task.completed = this.completed;
        return task;
    }
}