package ru.kacit.demo.test.server.dao.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.kacit.demo.test.server.model.Employee;
import ru.kacit.demo.test.server.model.Office;

import static org.junit.Assert.*;

/**
 * @author Serge Petunin
 *         18.03.12 12:12
 */
@ContextConfiguration("classpath:jpaOfficeDaoTestContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
public class JpaOfficeDaoTest extends JpaTest {

    @Autowired
    private JpaOfficeDao officeDao;
    
    @Test
    @Rollback
    @Transactional
    public void testDelete() {
        long employeeCount = count(Employee.class);
        long officeCount = count(Office.class);
        
        Office office = get(Office.class, 1L);
        assertNotNull(office);

        officeDao.delete(office);
        
        office = get(Office.class, 1L);
        assertNull(office);
        assertEquals(officeCount - 1, count(Office.class));
        assertEquals(employeeCount, count(Employee.class));
    }

    @Test
    @Rollback
    @Transactional
    public void testSave() {
        long employeeCount = count(Employee.class);
        long officeCount = count(Office.class);
        
        Office office = new Office();
        office.setName("New York Office");
        office.getEmployees().add(get(Employee.class, 1L));
        office.getEmployees().add(get(Employee.class, 2L));
        office.getEmployees().add(get(Employee.class, 3L));
        
        officeDao.saveOrUpdate(office);
        
        assertNotNull(office.getId());
        office = get(Office.class, office.getId());
        assertEquals(3, office.getEmployees().size());
        assertTrue(office.getEmployees().contains(get(Employee.class, 1L)));
        assertTrue(office.getEmployees().contains(get(Employee.class, 2L)));
        assertTrue(office.getEmployees().contains(get(Employee.class, 3L)));
        assertEquals(officeCount + 1, count(Office.class));
        assertEquals(employeeCount, count(Employee.class));
    }
    
    @Test
    @Rollback
    @Transactional
    public void testUpdate() {
        Office office = get(Office.class, 1L);
        office.getEmployees().remove(get(Employee.class, 1L));
        
        officeDao.saveOrUpdate(office);

        office = get(Office.class, 1L);
        assertEquals(1, office.getEmployees().size());
        assertTrue(office.getEmployees().contains(get(Employee.class, 2L)));
    }
    
    @Test
    @Rollback
    @Transactional(readOnly = true)
    public void testGetByNameExisting() {
        assertNotNull(officeDao.getByName("Boston Office"));
    }

    @Test
    @Rollback
    @Transactional(readOnly = true)
    public void testGetByNameNonExisting() {
        assertNull(officeDao.getByName("New York Office"));
    }

}
