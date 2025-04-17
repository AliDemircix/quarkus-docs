package com.tasktracker.exceptions;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public Response toResponse(Throwable exception) {
        int status = 500;
        String message = "Internal server error";

        if (exception instanceof WebApplicationException webEx) {
            status = webEx.getResponse().getStatus();
            message = webEx.getMessage();
        } else {
            // Log the full exception details for internal server errors
            logger.error("Unexpected error occurred", exception);
        }

        Map<String, Object> error = new HashMap<>();
        error.put("error", message);
        error.put("status", status);

        return Response.status(status)
                .entity(error)
                .type("application/json")
                .build();
    }
}
