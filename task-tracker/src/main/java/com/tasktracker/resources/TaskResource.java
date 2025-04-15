package com.tasktracker.resources;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.tasktracker.dto.TaskEntity;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    @POST
    @Transactional
    @Operation(summary = "Add new task", description = "This endpoint adds a new task to the task list.")
    public TaskEntity addTask(TaskEntity task) {
        task.persist();
        return task;
    }

    @GET
    public List<TaskEntity> getTasks() {
        return TaskEntity.listAll();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update task", description = "This endpoint updates an existing task in the task list.")
    public TaskEntity updateTask(@PathParam("id") Long id, TaskEntity updatedTask) {
        TaskEntity task = TaskEntity.findById(id);
        if (task == null) {
            throw new WebApplicationException("Task not found", 404);
        }
        task.title = updatedTask.title;
        task.description = updatedTask.description;
        task.completed = updatedTask.completed;
        return task;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete task with id", description = "Deleting specific task.")
    public void deleteTask(@PathParam("id") Long id) {
        TaskEntity.deleteById(id);
    }

    @GET
    @Path("/tasks/{id}")
    @Operation(summary = "Get task by id", description = "This endpoint retrieves a task by its ID.")
    public TaskEntity getTaskById(@PathParam("id") Long id) {
        TaskEntity task = TaskEntity.findById(id);
        if (task == null) {
            throw new WebApplicationException("Task not found", 404);
        }
        return task;
    }

}