package ru.kacit.demo.test.server.dao;

import ru.kacit.demo.test.server.model.Office;

/**
 * @author Serge Petunin
 *         18.03.12 12:08
 */
public interface OfficeDao {

    Office get(Long officeId);
    
    void delete(Office office);
    
    void saveOrUpdate(Office office);

    Office getByName(String name);
}
