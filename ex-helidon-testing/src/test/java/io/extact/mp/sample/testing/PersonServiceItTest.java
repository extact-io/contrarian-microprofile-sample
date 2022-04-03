package io.extact.mp.sample.testing;

import static org.junit.jupiter.api.Assertions.*;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.helidon.microprofile.tests.junit5.HelidonTest;

@HelidonTest
@ExtendWith(JulToSLF4DelegateExtension.class)
public class PersonServiceItTest {

    @Inject
    private PersonService service;

    @Test
    void testGetPerson() {
        var expected = new Person(1, "soramame", 18);
        var actual = service.get(1);
        assertEquals(expected, actual);
    }
}
