package io.extact.mp.sample.ft.client.external;

import java.util.Map;
import java.util.function.Supplier;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import io.extact.mp.sample.ft.client.exception.FatalException;
import io.extact.mp.sample.ft.client.exception.RetryableException;
import io.extact.mp.sample.ft.client.exception.SkipException;

public class HelloClientExceptionMapper implements ResponseExceptionMapper<RuntimeException> {

    private Map<String, Supplier<RuntimeException>> exceptionMapper = Map.of(
            RetryableException.class.getSimpleName(), RetryableException::new,
            FatalException.class.getSimpleName(), FatalException::new,
            SkipException.class.getSimpleName(), SkipException::new
            );

    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        return headers.containsKey("X-Exception");
    }

    @Override
    public RuntimeException toThrowable(Response response) {
        var className = response.getHeaderString("X-Exception");
        var mapper = exceptionMapper.getOrDefault(className, IllegalArgumentException::new);
        return mapper.get();
    }
}
