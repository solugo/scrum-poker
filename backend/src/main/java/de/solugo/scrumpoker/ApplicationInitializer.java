package de.solugo.scrumpoker;

import de.solugo.scrumpoker.data.entity.Account;
import de.solugo.scrumpoker.data.entity.AccountRole;
import de.solugo.scrumpoker.data.repository.AccountRepository;
import de.solugo.scrumpoker.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ApplicationInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final String ADMIN_NAME = "admin";

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        if (accountRepository.findByName(ADMIN_NAME) == null) {
            final Account adminAccount = new Account();
            adminAccount.setName(ADMIN_NAME);
            adminAccount.setPassword(SecurityService.PASSWORD_ENCODER.encode(ADMIN_NAME));
            adminAccount.setRole(AccountRole.ADMIN);
            accountRepository.save(adminAccount);
        }
    }
}
