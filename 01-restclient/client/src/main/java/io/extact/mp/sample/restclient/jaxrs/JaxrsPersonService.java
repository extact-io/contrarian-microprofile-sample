package io.extact.mp.sample.restclient.jaxrs;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response.Status;

import io.extact.mp.sample.restclient.Person;
import io.extact.mp.sample.restclient.PersonClientException;
import io.extact.mp.sample.restclient.PersonClientException.ClientError;
import io.extact.mp.sample.restclient.PersonService;

@ApplicationScoped
public class JaxrsPersonService implements PersonService {

    private Client client;
    private static final String BASE_URL = "http://localhost:7001/api/persons";

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
    }

    @Override
    public Person getPerson(long id) {
        // リクエスト送信
        var response = client
                .target(BASE_URL)
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .get();
        // 結果該当なし
        if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
            throw new PersonClientException(ClientError.NOT_FOUND);
        }
        // 結果取得
        return response.readEntity(Person.class);
    }

    @Override
    public Person addPerson(Person person) {
        // リクエスト送信
        var response = client
                .target(BASE_URL)
                .request()
                .post(Entity.json(person));
        // nameの値重複
        if (response.getStatus() == Status.CONFLICT.getStatusCode()) {
            throw new PersonClientException(ClientError.NAME_DEPULICATE);
        }
        // 結果取得
        return response.readEntity(Person.class);
    }

    @Override
    public List<Person> findPerson(String name) {
        // リクエスト送信
        var response = client
                .target(BASE_URL)
                .queryParam("name", name)
                .request()
                .get();
        // 結果取得
        return response.readEntity(new GenericType<List<Person>>() {});
    }

    @Override
    public void reset() {
        client.target(BASE_URL).path("reset").request().get();
    }
}
