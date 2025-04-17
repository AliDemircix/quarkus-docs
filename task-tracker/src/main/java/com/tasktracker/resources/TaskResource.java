package com.tasktracker.resources;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.tasktracker.dto.TaskDTO;
import com.tasktracker.models.TaskModel;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    @POST
    @Transactional
    @Operation(summary = "Add new task", description = "This endpoint adds a new task to the task list.")
    public Response addTask(@Valid TaskDTO taskDTO) {
        TaskModel task = taskDTO.toEntity();
        task.persist();
        return Response.status(201).entity(task).build();
    }

    @GET
    public List<TaskModel> getTasks() {
        return TaskModel.listAll();
    }

    @PUT
    @Path("/tasks/{id}")
    @Transactional
    @Operation(summary = "Update task", description = "This endpoint updates an existing task in the task list.")
    public Response updateTask(@PathParam("id") Long taskId, @Valid TaskDTO taskDTO) {
        TaskModel task = TaskModel.findById(taskId);
        if (task == null) {
            throw new NotFoundException("Task not found"); // 404 Not Found
        }
        task.title = taskDTO.title;
        task.description = taskDTO.description;
        task.completed = taskDTO.completed;
        return Response.ok().entity(task).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete task with id", description = "Deleting specific task.")
    public void deleteTask(@PathParam("id") Long id) {
        TaskModel.deleteById(id);
    }

    @GET
    @Path("/tasks/{id}")
    @Operation(summary = "Get task by id", description = "This endpoint retrieves a task by its ID.")
    public TaskModel getTaskById(@PathParam("id") Long id) {
        TaskModel task = TaskModel.findById(id);
        if (task == null) {
            throw new WebApplicationException("Task not found", 404);
        }
        return task;
    }

}