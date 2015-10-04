package de.solugo.scrumpoker.data.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Game extends Base {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Account owner;

    @Basic
    @Column(nullable = false)
    private String title;

    @Basic
    private String code;

}
