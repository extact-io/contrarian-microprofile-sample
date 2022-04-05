package io.extact.mp.sample.testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.microprofile.config.Config;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.helidon.microprofile.config.ConfigCdiExtension;
import io.helidon.microprofile.tests.junit5.AddBean;
import io.helidon.microprofile.tests.junit5.AddConfig;
import io.helidon.microprofile.tests.junit5.AddExtension;
import io.helidon.microprofile.tests.junit5.DisableDiscovery;
import io.helidon.microprofile.tests.junit5.HelidonTest;

import io.extact.mp.sample.testing.PersonServiceStubWithConfigTest.PersonRepositoryStub;

@HelidonTest
@DisableDiscovery
@AddBean(PersonService.class)
@AddBean(PersonRepositoryStub.class)
@AddExtension(ConfigCdiExtension.class)
@AddConfig(key = "test.name", value = "config-person")
@AddConfig(key = "test.age", value = "10")
@ExtendWith(JulToSLF4DelegateExtension.class)
public class PersonServiceStubWithConfigTest {

    @Inject
    private PersonService service;

    @Test
    void testGetPerson() {
        var expected = new Person(1, "config-person", 10);
        var actual = service.get(1);
        assertEquals(expected, actual);
    }

    // PersonRepositoryに対するスタブ実装
    static class PersonRepositoryStub implements PersonRepository {
        private Config config;

        @Inject
        PersonRepositoryStub(Config config) {
            this.config = config;
        }

        @Override
        public Person get(int id) {
            var name = config.getValue("test.name", String.class);
            var age = config.getValue("test.age", int.class);
            return new Person(id, name, age);
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
