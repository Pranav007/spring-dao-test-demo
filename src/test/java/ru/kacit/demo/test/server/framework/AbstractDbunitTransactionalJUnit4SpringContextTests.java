/*
 * (c) КАЦИТ, 2013. Все права защищены.
 */
package ru.kacit.demo.test.server.framework;

import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;

import javax.sql.DataSource;
import java.lang.reflect.Method;

/**
 * @author Serge Petunin
 * @created 21.06.13 1:36
 */
@TestExecutionListeners(
        AbstractDbunitTransactionalJUnit4SpringContextTests.DbunitTestExecutionListener.class
)
public abstract class AbstractDbunitTransactionalJUnit4SpringContextTests
        extends AbstractTransactionalJUnit4SpringContextTests {

    /** DBUnit tester */
    private IDatabaseTester databaseTester;

    /** expected dataset file name */
    private String afterDatasetFileName;

    /** method to execute after the test transaction is completed — verification */
    @AfterTransaction
    public void assertAfterTransaction() throws Exception {
        if (databaseTester == null || afterDatasetFileName == null) {
            return;
        }
        IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
        IDataSet expectedDataSet =
                new XmlDataSet(ClassLoader.getSystemResourceAsStream(afterDatasetFileName));
        Assertion.assertEquals(expectedDataSet, databaseDataSet);
        databaseTester.onTearDown();
    }

    public static class DbunitTestExecutionListener extends AbstractTestExecutionListener {

        /** method to execute before the test method — initialization */
        public void beforeTestMethod(TestContext testContext) throws Exception {
            AbstractDbunitTransactionalJUnit4SpringContextTests testInstance = (AbstractDbunitTransactionalJUnit4SpringContextTests) testContext.getTestInstance();
            Method method = testContext.getTestMethod();

            DbunitDataSets annotation = method.getAnnotation(DbunitDataSets.class);
            if (annotation == null) {
                return;
            }

            DataSource dataSource = testContext.getApplicationContext().getBean(DataSource.class);
            IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
            databaseTester.setDataSet(
                    new XmlDataSet(ClassLoader.getSystemResourceAsStream(annotation.before())));
            databaseTester.onSetup();
            testInstance.databaseTester = databaseTester;
            testInstance.afterDatasetFileName = annotation.after();
        }
    }
}