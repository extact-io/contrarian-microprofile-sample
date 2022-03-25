package io.extact.mp.sample.openapi;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import io.extact.mp.sample.openapi.resource.PersonException;

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
