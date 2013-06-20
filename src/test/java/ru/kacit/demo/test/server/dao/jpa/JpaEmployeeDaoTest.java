package ru.kacit.demo.test.server.dao.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.kacit.demo.test.server.model.Employee;
import ru.kacit.demo.test.server.model.Office;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Serge Petunin
 *         18.03.12 1:24
 */
@ContextConfiguration("classpath:jpaEmployeeDaoTestContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
@ActiveProfiles("jpa-test")
public class JpaEmployeeDaoTest extends JpaTest {

    @Autowired
    private JpaEmployeeDao employeeDao;

    @Test
    @Rollback
    @Transactional(readOnly = true)
    public void testGet() {
        Employee employee = employeeDao.get(1L);
        assertNotNull(employee);
        assertEquals("Chip", employee.getName());
    }

    @Test
    @Rollback
    @Transactional
    public void testDelete() {
        long employeeCount = count(Employee.class);
        long officeCount = count(Office.class);
        Employee employee = get(Employee.class, 1L);
        assertNotNull(employee);

        employeeDao.delete(employee);
        employee = get(Employee.class, 1L);

        assertNull(employee);
        assertEquals(employeeCount - 1, count(Employee.class));
        assertEquals(officeCount, count(Office.class));
    }

    @Test
    @Rollback
    @Transactional(readOnly = true)
    public void testListWithFilter() {

        List<Employee> employeeList = employeeDao.list("chip");

        assertEquals(1, employeeList.size());
        assertTrue(employeeList.contains(get(Employee.class, 1L)));
    }
    
    @Test
    @Rollback
    @Transactional(readOnly = true)
    public void testListAll() {
        List<Employee> employeeList = employeeDao.list(null);

        assertEquals(3, employeeList.size());
        assertTrue(employeeList.contains(get(Employee.class, 1L)));
        assertTrue(employeeList.contains(get(Employee.class, 2L)));
        assertTrue(employeeList.contains(get(Employee.class, 3L)));
    }

    @Test(expected = PersistenceException.class)
    @Rollback
    @Transactional
    public void testSaveWithoutName() {
        Employee employee = new Employee();
        employeeDao.saveOrUpdate(employee);
    }

    @Test
    @Rollback
    @Transactional
    public void testSave() {
        long employeeCount = count(Employee.class);
        
        Employee employee = new Employee();
        employee.setName("Monty");
        employeeDao.saveOrUpdate(employee);

        assertNotNull(employee.getId());
        assertEquals(employeeCount + 1, count(Employee.class));
    }

    @Test
    @Rollback
    @Transactional
    public void testUpdate() {
        Employee employee = get(Employee.class, 1L);
        employee.setName("Monty");
        employeeDao.saveOrUpdate(employee);

        employee = get(Employee.class, 1L);
        assertEquals("Monty", employee.getName());
    }

}
