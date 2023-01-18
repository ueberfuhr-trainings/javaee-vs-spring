package de.sample.javax.javaee.persistence;

import de.sample.javax.common.persistence.PersistenceConstants;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

/*
 * We want to use the container-managed EntityManagerFactory.
 * We only can get it by using @PersistenceContext. So we
 * let it inject by the container using CDI, and then access
 * the CDI managed bean by Spring.
 */
@ApplicationScoped
public class EntityManagerProducer {

    // TODO the EntityManager is not thread-safe!
    @PersistenceContext(name = PersistenceConstants.JPA_PERSISTENCE_UNIT_NAME)
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

    /*
     * This method is a solution to get the bean initialized on
     * application startup. We need this to be able to use
     * the container-managed entity manager within Spring Data.
     * This avoids creating a custom EntityManagerFactory in Spring,
     * and is helpful to use the server's original JPA Persistence Provider.
     */
    public void init(
      @Observes @Initialized(ApplicationScoped.class)
      Object init
    ) {
        // do nothing
    }

}
