package ru.kacit.demo.test.server.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * @author Serge Petunin
 *         18.03.12 3:12
 */
public class JpaTest {
    
    @PersistenceContext
    private EntityManager em;

    protected <T> T get(Class<T> objectClass, Long id) {
        return em.find(objectClass, id);
    }

    protected <T> long count(Class<T> objectClass) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        query.select(cb.count(query.from(objectClass)));
        return em.createQuery(query).getSingleResult();
    }

}
