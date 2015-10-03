package de.solugo.scrumpoker.data.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Account extends Base {

    @Basic
    @Column(nullable = false, unique = true)
    private String name;

    @Basic
    @Column(nullable = false, unique = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;

}
