package io.extact.mp.sample.restclient.resource;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.helidon.microprofile.tests.junit5.AddConfig;
import io.helidon.microprofile.tests.junit5.HelidonTest;

@HelidonTest
@AddConfig(key = "server.port", value = "7001")
@ExtendWith(JulToSLF4DelegateExtension.class)
public class PersonResourceTest {

    private PersonResource personResource;

    @BeforeEach
    public void setup() throws Exception {
        personResource = RestClientBuilder.newBuilder()
                .baseUri(new URI("http://localhost:7001/api/persons"))
                .build(PersonResource.class);
    }

    @Test
    void tesGetPerson() {
        var expected = new Person(1L, "taro", 12);
        var actual = personResource.get(1L);
        assertEquals(expected, actual);
    }

    @Test
    void testGetPersonOnNotFound() {
        var exception = assertThrows(WebApplicationException.class, () -> {
            personResource.get(99L);
        });
        assertEquals(Status.NOT_FOUND.getStatusCode(), exception.getResponse().getStatus());
    }

    @Test
    void tesAddPerson() {
        var newPerson = new Person(null, "add-person", 32);
        var addedPerson = personResource.add(newPerson);
        assertNotNull(addedPerson.getId());
    }

    @Test
    void testAddPersonOnDuplicated() {
        var exception = assertThrows(WebApplicationException.class, () -> {
            personResource.add(new Person(null, "hanko", 50));
        });
        assertEquals(Status.CONFLICT.getStatusCode(), exception.getResponse().getStatus());
    }

    @Test
    void tesFindPerson() {
        var actual = personResource.findByName("bo");
        assertEquals(1, actual.size());
    }

    @Test
    void testFindPersonOnNotFound() {
        var actual = personResource.findByName("xx");
        assertEquals(0, actual.size());
    }
}
