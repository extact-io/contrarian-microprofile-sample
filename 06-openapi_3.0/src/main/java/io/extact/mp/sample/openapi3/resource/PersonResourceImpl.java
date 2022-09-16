package io.extact.mp.sample.openapi3.resource;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import io.extact.mp.sample.openapi3.resource.PersonException.CauseError;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;

@ApplicationScoped
@Path("persons")
public class PersonResourceImpl implements PersonResource  {

    private Map<Long, Person> personMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        personMap.put(1L,
                new Person(1L, "taro", LocalDate.of(1974, 7, 22), Map.of("nickname", "tarochan", "rank", "hira")));
        personMap.put(2L,
                new Person(2L, "hanko", LocalDate.of(1977, 2, 13), Map.of("nickname", "hanachan", "rank", "hira")));
        personMap.put(3L,
                new Person(3L, "bob", LocalDate.of(2011, 1, 14), Map.of("nickname", "bob", "rank", "boss")));
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
