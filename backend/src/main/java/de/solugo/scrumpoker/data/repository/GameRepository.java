package de.solugo.scrumpoker.data.repository;

import de.solugo.scrumpoker.data.entity.Account;
import de.solugo.scrumpoker.data.entity.Game;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GameRepository extends BaseRepository<Game> {

    public GameRepository() {
        super(Game.class);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Game createGame(final Long accountId, final Game game) {
        game.setOwner(entityManager.getReference(Account.class, accountId));
        return save(game);
    }
}
