package com.tasktracker.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.tasktracker.dto.TaskDTO;
import com.tasktracker.dto.TaskResponseDTO;
import com.tasktracker.dto.UserDTO;
import com.tasktracker.models.TaskModel;
import com.tasktracker.models.UserModel;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class UserResource {
    @POST
    @Transactional
    public UserModel createUser(@Valid UserDTO userDTO) {
        UserModel user = new UserModel();
        user.name = userDTO.name;
        user.email = userDTO.email;
        user.persist();
        return user;
    }

    @GET
    public List<UserModel> getAllUsers() {
        return UserModel.listAll();
    }

    @GET
    @Path("/{id}/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TaskModel> getTasksForUser(@PathParam("id") Long userId) {
        UserModel user = UserModel.findById(userId);
        if (user == null) {
            throw new WebApplicationException("User not found", 404);
        }
        return TaskModel.list("user.id", user.id);
    }

    @POST
    @Path("/{id}/tasks")
    @Transactional
    public Response addTaskToUser(@PathParam("id") Long userId, @Valid TaskDTO taskDTO) {
        UserModel user = UserModel.findById(userId);
        if (user == null) {
            throw new WebApplicationException("User not found", 404);
        }
        TaskModel task = taskDTO.toEntity();

        task.user = user;
        task.persist();
        return Response.status(Response.Status.CREATED).entity(task).build();
    }

    @PUT
    @Path("/{userId}/tasks/{taskId}")
    @Transactional
    @Operation(summary = "Update task for user", description = "This endpoint updates an existing task for a specific user.")
    public Response updateUserTask(@PathParam("userId") Long userId, @PathParam("taskId") Long taskId,
            @Valid TaskDTO taskDTO) {
        UserModel user = UserModel.findById(userId);
        if (user == null) {
            throw new WebApplicationException("User not found", 404);
        }
        TaskModel task = TaskModel.findById(taskId);
        if (task == null || !task.user.id.equals(userId)) {
            throw new WebApplicationException("Task not found for this user", 404);
        }
        task.title = taskDTO.title;
        task.description = taskDTO.description;
        task.completed = taskDTO.completed;
        return Response.ok().entity(task).build();
    }

    @DELETE
    @Path("/{userId}/tasks/{taskId}")
    @Transactional
    @Operation(summary = "Delete task for user", description = "This endpoint deletes a specific task for a specific user.")
    public Response deleteUserTask(@PathParam("userId") Long userId, @PathParam("taskId") Long taskId) {
        UserModel user = UserModel.findById(userId);
        if (user == null) {
            throw new WebApplicationException("User not found", 404);
        }
        TaskModel task = TaskModel.findById(taskId);
        if (task == null || !task.user.id.equals(userId)) {
            throw new WebApplicationException("Task not found for this user", 404);
        }
        task.delete();
        return Response.noContent().build();
    }

    @GET
    @Path("/{userId}/tasks/completed")
    @Operation(summary = "Get completed tasks for user", description = "This endpoint retrieves all completed tasks for a specific user.")
    public List<TaskResponseDTO.TaskResponse> getCompletedTasksInRange(
            @PathParam("userId") Long userId,
            @QueryParam("startDate") String startDateStr,
            @QueryParam("endDate") String endDateStr) {
        UserModel user = UserModel.findById(userId);
        if (user == null) {
            throw new WebApplicationException("User with ID " + userId + " not found", 404);
        }
        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(startDateStr);
            endDate = LocalDate.parse(endDateStr);
        } catch (DateTimeParseException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(Collections.singletonMap("error", "Invalid date format. Please use YYYY-MM-DD"))
                            .type(MediaType.APPLICATION_JSON)
                            .build());
        }
        LocalDateTime start = startDate.atStartOfDay(); // 2025-04-15 00:00:00
        LocalDateTime end = endDate.atTime(23, 59, 59); // 2025-04-15 23:59:59
        List<TaskModel> tasks = TaskModel.list("user = ?1 and completed = true and createdAt between ?2 and ?3",
                user, start, end);

        // Here we use TaskResponseDTO.TaskResponse to transform each task
        return tasks.stream()
                .map(TaskResponseDTO.TaskResponse::new) // Transforming TaskModel to TaskResponseDTO
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}/tasks/completed/count")
    public Long countCompletedTasksForUser(
            @PathParam("id") Long id,
            @QueryParam("start") String startDateStr,
            @QueryParam("end") String endDateStr) {
        UserModel user = UserModel.findById(id);
        if (user == null) {
            throw new WebApplicationException("User not found", 404);
        }

        if (startDateStr == null || endDateStr == null) {
            throw new WebApplicationException("Start and end dates must be provided", 400);
        }

        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(startDateStr);
            endDate = LocalDate.parse(endDateStr);
        } catch (DateTimeParseException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(Collections.singletonMap("error", "Invalid date format. Please use YYYY-MM-DD"))
                            .type(MediaType.APPLICATION_JSON)
                            .build());
        }

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        return TaskModel.count("user = ?1 and completed = true and createdAt between ?2 and ?3", user, start, end);
    }

}
