package ru.kacit.demo.test.server.dao.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.kacit.demo.test.server.dao.EmployeeDao;
import ru.kacit.demo.test.server.framework.AbstractDbunitTransactionalJUnit4SpringContextTests;
import ru.kacit.demo.test.server.framework.DbunitDataSets;
import ru.kacit.demo.test.server.model.Employee;

import static org.junit.Assert.assertNotNull;

/**
 * @author Serge Petunin
 *         18.03.12 17:14
 */
@ContextConfiguration("classpath:jpaEmployeeDaoTestContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DbUnitJpaEmployeeDaoTest extends AbstractDbunitTransactionalJUnit4SpringContextTests {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    @Rollback(false)
    @DbunitDataSets(before = "dbunit/employeeInitialDataSet.xml", after = "dbunit/employeeDeleteExpectedDataSet.xml")
    @DirtiesContext
    public void testDelete() throws Exception {
        Employee employee = employeeDao.get(1L);
        assertNotNull(employee);
        employeeDao.delete(employee);
    }
    
}
