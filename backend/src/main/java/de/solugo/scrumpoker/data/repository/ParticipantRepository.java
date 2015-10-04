package de.solugo.scrumpoker.data.repository;

import de.solugo.scrumpoker.data.entity.Account;
import de.solugo.scrumpoker.data.entity.Game;
import de.solugo.scrumpoker.data.entity.Participant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ParticipantRepository extends BaseRepository<Participant> {

    public ParticipantRepository() {
        super(Participant.class);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Participant findByGameAndId(final Long gameId, final Long participantId) {
        final TypedQuery<Participant> query = createQuery("SELECT p FROM Participant p WHERE p.game.id = :gameId AND p.id = :participantId");
        query.setParameter("gameId", gameId);
        query.setParameter("participantId", participantId);

        return findByQuery(query);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Participant findByGameAndAccountId(final Long gameId, final Long accountId) {
        final TypedQuery<Participant> query = createQuery("SELECT p FROM Participant p WHERE p.game.id = :gameId AND p.account.id = :accountId");
        query.setParameter("gameId", gameId);
        query.setParameter("accountId", accountId);

        return findByQuery(query);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Participant> findAllByGame(final Long gameId) {
        final TypedQuery<Participant> query = createQuery("SELECT p FROM Participant p WHERE p.game.id = :gameId");
        query.setParameter("gameId", gameId);

        return findAllByQuery(query);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Participant addParticipantToGame(final Long gameId, final Long accountId, final Participant participant) {
        participant.setGame(entityManager.getReference(Game.class, gameId));
        participant.setAccount(entityManager.getReference(Account.class, accountId));
        return this.save(participant);
    }
}
