package io.extact.mp.sample.restclient;

import java.util.List;

public interface PersonService {
    Person getPerson(long id);
    Person addPerson(Person person);
    List<Person> findPerson(String name);
    void reset();
}
