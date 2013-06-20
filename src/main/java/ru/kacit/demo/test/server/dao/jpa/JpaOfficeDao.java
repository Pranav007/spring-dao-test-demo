package ru.kacit.demo.test.server.dao.jpa;

import ru.kacit.demo.test.server.dao.OfficeDao;
import ru.kacit.demo.test.server.model.Office;
import ru.kacit.demo.test.server.model.Office_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Serge Petunin
 *         18.03.12 12:08
 */
public class JpaOfficeDao implements OfficeDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Office get(Long officeId) {
        return em.find(Office.class, officeId);
    }

    @Override
    public Office getByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Office> query = cb.createQuery(Office.class);
        Root<Office> officeRoot = query.from(Office.class);
        if (name != null && !name.isEmpty()) {
            query.where(cb.equal(officeRoot.get(Office_.name), name));
        }
        List<Office> offices = em.createQuery(query).getResultList();
        if (offices == null || offices.isEmpty()) {
            return null;
        }
        return offices.get(0);
    }
    
    @Override
    public void delete(Office office) {
        em.remove(office);
    }

    @Override
    public void saveOrUpdate(Office office) {
        if (office.getId() == null) {
            em.persist(office);
        } else {
            em.merge(office);
        }
    }

}
