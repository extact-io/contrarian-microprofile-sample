package io.extact.mp.sample.ft.service;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;

public class HelloServiceExceptionMapper implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException exception) {
        return Response
                .status(Status.NO_CONTENT)
                .header("X-Exception", exception.getClass().getSimpleName())
                .build();
    }
}
