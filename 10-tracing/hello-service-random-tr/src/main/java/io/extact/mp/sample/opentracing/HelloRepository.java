package io.extact.mp.sample.opentracing;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class HelloRepository {

    @PersistenceContext
    private EntityManager em;

    public String get(int id) {
        return (String) em.createNativeQuery("select val from hello where id = ?")
                .setParameter(1, id)
                .getResultList()
                .get(0);
    }
}
