package de.sample.javax.todos.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProducer {

    @Produces
    @PersistenceContext(name = "puTodoDB")
    EntityManager em;

}
