package io.extact.mp.sample.testing;

import static org.junit.jupiter.api.Assertions.*;

import javax.enterprise.inject.spi.CDI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import io.helidon.microprofile.tests.junit5.HelidonTest;

@HelidonTest(resetPerTest = true)
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(JulToSLF4DelegateExtension.class)
public class PersonServiceItAllTest {

    //@Inject
    private PersonService service;

    @BeforeEach
    void init() {
        this.service = CDI.current().select(PersonService.class).get();
    }

    @Test
    @Order(2)
    void testGetPerson() {
        var expected = new Person(1, "soramame", 18);
        var actual = service.get(1);
        assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    void testGetAllPerson() {
        var expected = 2;
        var actual = service.getAll();
        assertEquals(expected, actual.size());
    }

    @Test
    @Order(1)
    void testAddPerson() {
        var newPerson = new Person(null, "add-person", 32);
        var addedPerson = service.add(newPerson);
        assertNotNull(addedPerson.getId());
    }
}
