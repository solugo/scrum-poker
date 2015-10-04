package de.solugo.scrumpoker.data.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(indexes = {
    @Index(columnList = "account_id, game_id", unique = true)
})
public class Participant extends Base {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Game game;

    private Integer selection;
}
