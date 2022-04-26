package io.extact.mp.sample.restclient.mp;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.extact.mp.sample.restclient.Person;
import io.extact.mp.sample.restclient.PersonService;

@ApplicationScoped
public class RestClientPersonService implements PersonService {

    private PersonClient personClient;

    @Inject
    public RestClientPersonService(@RestClient PersonClient personClient) {
        this.personClient = personClient;
    }

    @Override
    public Person getPerson(long id) {
        return personClient.get(id);
    }

    @Override
    public Person addPerson(Person person) {
        return personClient.add(person);
    }

    @Override
    public List<Person> findPerson(String name) {
        return personClient.findByName(name);
    }

    @Override
    public void reset() {
        personClient.reset();
    }
}
