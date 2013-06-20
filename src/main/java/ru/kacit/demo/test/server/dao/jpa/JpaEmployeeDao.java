package ru.kacit.demo.test.server.dao.jpa;

import org.springframework.stereotype.Repository;
import ru.kacit.demo.test.server.dao.EmployeeDao;
import ru.kacit.demo.test.server.model.Employee;
import ru.kacit.demo.test.server.model.Employee_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Serge Petunin
 *         18.03.12 0:42
 */
@Repository
public class JpaEmployeeDao implements EmployeeDao {

    @PersistenceContext
    private EntityManager em;
    
    public Employee get(Long id) {
        return em.find(Employee.class, id);
    }

    public List<Employee> list(String nameStartsWith) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> employeeRoot = query.from(Employee.class);
        if (nameStartsWith != null && !nameStartsWith.isEmpty()) {
            query.where(cb.like(cb.lower(employeeRoot.get(Employee_.name)), nameStartsWith.toLowerCase() + '%'));
        }
        return em.createQuery(query).getResultList();
    }
    
    public void saveOrUpdate(Employee employee) {
        if (employee.getId() == null) {
            em.persist(employee);
        } else {
            em.merge(employee);
        }
    }

    public void delete(Employee employee) {
        em.remove(employee);
    }

}
