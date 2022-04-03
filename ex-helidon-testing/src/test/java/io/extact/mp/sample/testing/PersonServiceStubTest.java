package io.extact.mp.sample.testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.helidon.microprofile.tests.junit5.AddBean;
import io.helidon.microprofile.tests.junit5.DisableDiscovery;
import io.helidon.microprofile.tests.junit5.HelidonTest;

import io.extact.mp.sample.testing.PersonServiceStubTest.PersonRepositoryStub;

@HelidonTest
@DisableDiscovery
@AddBean(PersonService.class)
@AddBean(PersonRepositoryStub.class)
@ExtendWith(JulToSLF4DelegateExtension.class)
public class PersonServiceStubTest {

    @Inject
    private PersonService service;

    @Test
    void testGetPerson() {
        var expected = new Person(1, "stub-person", 99);
        var actual = service.get(1);
        assertEquals(expected, actual);
    }

    // PersonRepositoryに対するスタブ実装
    static class PersonRepositoryStub implements PersonRepository {
        @Override
        public Person get(int id) {
            return new Person(id, "stub-person", 99);
        }
        @Override
        public List<Person> findAll() {
            throw new UnsupportedOperationException();
        }
        @Override
        public Person add(Person person) {
            throw new UnsupportedOperationException();
        }
    }
}
