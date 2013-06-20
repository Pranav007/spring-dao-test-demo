package ru.kacit.demo.test.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import ru.kacit.demo.test.server.dao.jpa.JpaTest;
import ru.kacit.demo.test.server.model.DomainObject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;

/**
 * @author Serge Petunin
 *         18.03.12 12:01
 */
public class TestDatabaseBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(TestDatabaseBootstrap.class);

    @PersistenceContext
    private EntityManager em;

    private String bootstrapContextFile;

    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Populating database from Spring context file " + bootstrapContextFile);
        ClassPathXmlApplicationContext databaseBootstrapContext
                = new ClassPathXmlApplicationContext(bootstrapContextFile);
        Map<String, DomainObject> beans = databaseBootstrapContext.getBeansOfType(DomainObject.class);
        for (Map.Entry<String, DomainObject> entry: beans.entrySet()) {
            log.info(String.format("Persisting bean %s of type %s",
                    entry.getKey(), entry.getValue().getClass().getName()));
            em.persist(entry.getValue());
        }
        databaseBootstrapContext.close();
    }

    public void setBootstrapContextFile(String bootstrapContextFile) {
        this.bootstrapContextFile = bootstrapContextFile;
    }

}
