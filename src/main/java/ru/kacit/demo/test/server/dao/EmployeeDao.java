package ru.kacit.demo.test.server.dao;

import ru.kacit.demo.test.server.model.Employee;

import java.util.List;

/**
 * @author Serge Petunin
 *         18.03.12 0:53
 */
public interface EmployeeDao {
    
    Employee get(Long id);

    List<Employee> list(String nameStartsWith);

    void saveOrUpdate(Employee employee);

    void delete(Employee employee);

}
