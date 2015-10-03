package de.solugo.scrumpoker.data.repository;

import de.solugo.scrumpoker.data.entity.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class AccountRepository extends BaseRepository<Account> {

    public AccountRepository() {
        super(Account.class);
    }

    public Account findByName(final String name) {
        final TypedQuery<Account> query = this.createQuery("SELECT a FROM Account a WHERE a.name = :name");
        query.setParameter("name", name);

        return this.findByQuery(query);
    }
}
