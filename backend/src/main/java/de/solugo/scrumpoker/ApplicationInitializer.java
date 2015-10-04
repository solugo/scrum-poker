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

    private static final String ADMIN_EMAIL = "admin@poker";
    private static final String ADMIN_PASSWORD = "admin";

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        if (accountRepository.findByEmail(ADMIN_EMAIL + "") == null) {
            final Account adminAccount = new Account();
            adminAccount.setEmail(ADMIN_EMAIL);
            adminAccount.setPassword(SecurityService.PASSWORD_ENCODER.encode(ADMIN_PASSWORD));
            adminAccount.setRole(AccountRole.ADMIN);
            accountRepository.save(adminAccount);
        }
    }
}
