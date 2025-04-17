package com.tasktracker.dto;

import java.time.LocalDateTime;

import com.tasktracker.models.TaskModel;

public class TaskResponseDTO {

    public static class TaskResponse {
        public Long id;
        public String title;
        public String description;
        public Boolean completed;
        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;

        // Constructor
        public TaskResponse() {
            // Default constructor
        }

        public TaskResponse(TaskModel task) {
            this.id = task.id;
            this.title = task.title;
            this.description = task.description;
            this.completed = task.completed;
            this.createdAt = task.createdAt;
            this.updatedAt = task.updatedAt;
        }
    }
}
