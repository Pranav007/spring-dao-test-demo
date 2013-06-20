package ru.kacit.demo.test.server.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.kacit.demo.test.server.dao.jpa.JpaTest;
import ru.kacit.demo.test.server.model.Employee;
import ru.kacit.demo.test.server.model.Office;
import ru.kacit.demo.test.server.service.EmployeeService;

import static org.junit.Assert.*;

/**
 * @author Serge Petunin
 *         18.03.12 14:18
 */
@ContextConfiguration("classpath:genericEmployeeServiceTestContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
public class GenericEmployeeServiceTest extends JpaTest {

    @Autowired
    private EmployeeService service;

    @Test
    @Transactional
    @Rollback
    public void testCreateEmployeeWithNoOffice() {
        long employeeCount = count(Employee.class);
        
        Employee employee = new Employee();
        employee.setName("Monty");
        service.createEmployee(employee, null);
        
        assertEquals(employeeCount + 1, count(Employee.class));
        assertNotNull(employee.getId());
        employee = get(Employee.class, employee.getId());
        assertNotNull(employee);
        assertTrue(get(Office.class, 1L).getEmployees().contains(get(Employee.class, employee.getId())));
    }
    
    @Test
    @Transactional
    @Rollback
    public void testCreateEmployeeWithOffice() {
        long employeeCount = count(Employee.class);
        
        Employee employee = new Employee();
        employee.setName("Monty");
        service.createEmployee(employee, 2L);
        
        assertEquals(employeeCount + 1, count(Employee.class));
        assertNotNull(employee.getId());
        employee = get(Employee.class, employee.getId());
        assertNotNull(employee);
        assertTrue(get(Office.class, 2L).getEmployees().contains(get(Employee.class, employee.getId())));
    }
    
}
