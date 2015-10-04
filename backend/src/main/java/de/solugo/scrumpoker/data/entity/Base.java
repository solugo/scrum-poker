package de.solugo.scrumpoker.data.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class Base implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Date version;
}
