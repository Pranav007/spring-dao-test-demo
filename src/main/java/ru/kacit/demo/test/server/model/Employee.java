package ru.kacit.demo.test.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Serge Petunin
 *         18.03.12 0:29
 */
@Entity
public class Employee extends DomainObject {

    @Column(nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
