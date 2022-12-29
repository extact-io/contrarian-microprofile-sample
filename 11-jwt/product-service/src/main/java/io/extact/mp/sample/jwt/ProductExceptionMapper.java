package io.extact.mp.sample.jwt;


import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;

import io.extact.mp.sample.jwt.resource.ProductException;

public class ProductExceptionMapper implements ExceptionMapper<ProductException> {
    @Override
    public Response toResponse(ProductException exception) {
        Status status = switch (exception.getCauseError()) {
            case NOT_FOUND -> status = Status.NOT_FOUND; // 404
            case DUPLICATE -> status = Status.CONFLICT;  // 409
        };
        return Response
                .status(status)
                .entity(status.getReasonPhrase())
                .build();
    }
}
