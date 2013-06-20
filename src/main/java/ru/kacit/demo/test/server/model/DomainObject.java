package ru.kacit.demo.test.server.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Serge Petunin
 *         18.03.12 2:09
 */
@MappedSuperclass
public class DomainObject implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
