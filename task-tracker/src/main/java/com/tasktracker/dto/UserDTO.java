package com.tasktracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    @NotBlank(message = "Name cannot be blank") // Validation annotation
    public String name;

    @NotBlank(message = "Email cannot be blank") // Validation annotation
    @Email(message = "Email should be valid") // Validation annotation
    public String email;
}
