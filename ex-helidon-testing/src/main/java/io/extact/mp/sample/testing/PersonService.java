package io.extact.mp.sample.testing;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class PersonService {

    private PersonRepository repository;

    @Inject
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Person get(int id) {
        return repository.get(id);
    }

    public List<Person> getAll() {
        return repository.findAll();
    }

    public Person add(Person person) {
        return repository.add(person);
    }
}
