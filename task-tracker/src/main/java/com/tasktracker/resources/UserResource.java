package com.tasktracker.resources;

import java.util.List;

import com.tasktracker.dto.TaskDTO;
import com.tasktracker.dto.UserDTO;
import com.tasktracker.models.TaskModel;
import com.tasktracker.models.UserModel;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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
    public List<TaskModel> getTasksForUser(@PathParam("id") Long id) {
        UserModel user = UserModel.findById(id);
        if (user == null) {
            throw new WebApplicationException("User not found", 404);
        }
        return user.tasks;
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

}
