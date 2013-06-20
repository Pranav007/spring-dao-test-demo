package ru.kacit.demo.test.server.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Serge Petunin
 *         18.03.12 0:27
 */
@Entity
public class Office extends DomainObject {
    
    private String name;
    
    @OneToMany(targetEntity = Employee.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn
    private Set<Employee> employees = new HashSet<Employee>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

}
