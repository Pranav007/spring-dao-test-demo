package ru.kacit.demo.test.server.dao.jpa;

import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import ru.kacit.demo.test.server.dao.EmployeeDao;
import ru.kacit.demo.test.server.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import static org.junit.Assert.assertNotNull;

/**
 * @author Serge Petunin
 *         18.03.12 17:14
 */
@ContextConfiguration("classpath:jpaEmployeeDaoTestContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DbUnitJpaEmployeeDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    private IDatabaseTester databaseTester;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DataSource dataSource;

    @Before
    public void setUp() throws Exception {
        databaseTester = new DataSourceDatabaseTester(dataSource);
        databaseTester.setDataSet(new XmlDataSet(
                ClassLoader.getSystemResourceAsStream("dbunit/employeeInitialDataSet.xml")));
        databaseTester.onSetup();
    }

    @Test
    @Rollback(false)
    public void testDelete() throws Exception {
        Employee employee = employeeDao.get(1L);
        assertNotNull(employee);
        employeeDao.delete(employee);
    }
    
    @AfterTransaction
    public void assertAfterTransaction() throws Exception {
        IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
        IDataSet expectedDataSet = new XmlDataSet(
                ClassLoader.getSystemResourceAsStream("dbunit/employeeDeleteExpectedDataSet.xml"));
        Assertion.assertEquals(expectedDataSet, databaseDataSet);
    }

    @After
    public void tearDown() throws Exception {
        databaseTester.onTearDown();
    }

}
