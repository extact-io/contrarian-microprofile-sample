package io.extact.mp.sample.openapi3;

import io.extact.mp.sample.openapi3.resource.PersonException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;

public class PersonExceptionMapper implements ExceptionMapper<PersonException> {
    @Override
    public Response toResponse(PersonException exception) {
        Status status = null;
        switch (exception.getCauseError()) {
            case NOT_FOUND:
                status = Status.NOT_FOUND;
                break;
            case CONFLICT:
                status = Status.CONFLICT;
                break;
        }
        return Response
                .status(status)
                .entity(status.getReasonPhrase())
                .build();
    }
}
