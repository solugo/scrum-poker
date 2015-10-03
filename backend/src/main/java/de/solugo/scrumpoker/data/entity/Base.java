package de.solugo.scrumpoker.data.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class Base implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

}
