package io.extact.mp.sample.testing;

import java.util.List;

public interface PersonRepository {
    Person get(int id);
    List<Person> findAll();
    Person add(Person person);
}
