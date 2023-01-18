package de.sample.javax.todos.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceContext(name = "puTodoDB")
    EntityManager em;

    @Produces
    EntityManagerFactory entityManagerFactory() {
        return em.getEntityManagerFactory();
    }

    /*
     * EntityManager is not thread-safe, so we need to inject
     * one Entity Manager per request.
     */
    @Produces
    EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }
}
