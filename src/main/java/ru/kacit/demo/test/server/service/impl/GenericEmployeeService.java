package ru.kacit.demo.test.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kacit.demo.test.server.dao.EmployeeDao;
import ru.kacit.demo.test.server.dao.OfficeDao;
import ru.kacit.demo.test.server.model.Employee;
import ru.kacit.demo.test.server.model.Office;
import ru.kacit.demo.test.server.service.EmployeeService;

/**
 * @author Serge Petunin
 *         18.03.12 14:09
 */
@Service
public class GenericEmployeeService implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;
    
    @Autowired
    private OfficeDao officeDao;
    
    @Override
    @Transactional
    public void createEmployee(Employee employee, Long officeId) {
        employeeDao.saveOrUpdate(employee);
        Office office;
        if (officeId == null) {
            office = officeDao.getByName("Boston Office");
        } else {
            office = officeDao.get(officeId);
        }
        office.getEmployees().add(employee);
        officeDao.saveOrUpdate(office);
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void setOfficeDao(OfficeDao officeDao) {
        this.officeDao = officeDao;
    }

}
