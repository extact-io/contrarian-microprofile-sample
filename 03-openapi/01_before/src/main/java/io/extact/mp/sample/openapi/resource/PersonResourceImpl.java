package io.extact.mp.sample.openapi.resource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;

import io.extact.mp.sample.openapi.resource.PersonException.CauseError;

@ApplicationScoped
@Path("persons")
public class PersonResourceImpl implements PersonResource  {

    private Map<Long, Person> personMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        personMap.put(1L, new Person(1L, "taro", 12));
        personMap.put(2L, new Person(2L, "hanko", 9));
        personMap.put(3L, new Person(3L, "bob", 15));
    }

    @Override
    public Person get(long id) {
        if (!personMap.containsKey(id)) {
            throw new PersonException(CauseError.NOT_FOUND);
        }
        return personMap.get(id);
    }

    @Override
    public Person add(Person person) {
        // name の重複は許可しない
        personMap.values().stream()
                .filter(p -> p.getName().equals(person.getName()))
                .findAny()
                .ifPresent(p -> {
                    throw new PersonException(CauseError.CONFLICT);
                });
        // Personの登録
        var nextId = personMap.keySet().stream().max(Long::compareTo).get() + 1;
        var newPerson = person.withId(nextId);
        personMap.put(nextId, newPerson);
        return newPerson;
    }

    @Override
    public List<Person> findByName(String name) {
        return personMap.values().stream()
                .filter(p -> p.getName().startsWith(name))
                .collect(Collectors.toList());
    }
}
