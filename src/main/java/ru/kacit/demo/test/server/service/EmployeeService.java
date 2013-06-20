package ru.kacit.demo.test.server.service;

import ru.kacit.demo.test.server.model.Employee;

/**
 * @author Serge Petunin
 *         18.03.12 14:08
 */
public interface EmployeeService {

    void createEmployee(Employee employee, Long officeId);

}
