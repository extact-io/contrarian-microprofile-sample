package io.extact.mp.sample.ft.client.external;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

public class TimestampHeaderFactory implements ClientHeadersFactory {
    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
            MultivaluedMap<String, String> clientOutgoingHeaders) {
        var newHeadersMap = new MultivaluedHashMap<String, String>(clientOutgoingHeaders);
        if (!incomingHeaders.containsKey("X-Suppress-Timestamp")) {
            newHeadersMap.add("X-Requested-Timestamp", generateTimestamp());
        }
        return newHeadersMap;
    }
    private String generateTimestamp() {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");
        return formatter.format(LocalDateTime.now());
    }
}
