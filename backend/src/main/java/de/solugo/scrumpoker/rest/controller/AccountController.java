package de.solugo.scrumpoker.rest.controller;

import de.solugo.scrumpoker.data.entity.Account;
import de.solugo.scrumpoker.data.repository.AccountRepository;
import de.solugo.scrumpoker.rest.resource.AccountResource;
import de.solugo.scrumpoker.service.SecurityService;
import de.solugo.scrumpoker.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/rest/accounts")
public class AccountController extends BaseController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BeanMapper beanMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public List<AccountResource> listAllAccounts() {
        return beanMapper.map(accountRepository.findAll(), AccountResource.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public AccountResource saveAccount(@RequestBody final AccountResource accountResource) {
        return beanMapper.map(accountRepository.merge(accountResource.getId(), accountResource), AccountResource.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public AccountResource findAccount(@PathVariable final Long accountId) {
        return beanMapper.map(accountRepository.findById(accountId), AccountResource.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{accountId}", method = RequestMethod.POST)
    public AccountResource saveAccount(@PathVariable final Long accountId, @RequestBody final AccountResource accountResource) {
        final Account account = accountRepository.findById(accountId);
        beanMapper.map(accountResource, account);
        return beanMapper.map(accountRepository.save(account), AccountResource.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{accountId}/password", method = RequestMethod.POST)
    public void changeAccountPassword(@PathVariable final Long accountId, @RequestBody String password) {
        final Account account = accountRepository.findById(accountId);
        account.setPassword(SecurityService.PASSWORD_ENCODER.encode(password));
        accountRepository.save(account);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/self", method = RequestMethod.GET)
    public AccountResource findLoggedInAccount(@AuthenticationPrincipal final String accountName) {
        return beanMapper.map(accountRepository.findByName(accountName), AccountResource.class);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/self", method = RequestMethod.POST)
    public AccountResource saveLoggedInAccount(@AuthenticationPrincipal final String accountName, @RequestBody final AccountResource accountResource, final HttpServletRequest request) throws ServletException {
        final Account account = accountRepository.findByName(accountName);
        beanMapper.map(accountResource, account);

        request.logout();
        return beanMapper.map(accountRepository.save(account), AccountResource.class);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/self/password", method = RequestMethod.POST)
    public void changeLoggedInAccountPassword(@AuthenticationPrincipal final String accountName, @RequestBody String password, final HttpServletRequest request) throws ServletException {
        final Account account = accountRepository.findByName(accountName);
        account.setPassword(SecurityService.PASSWORD_ENCODER.encode(password));
        accountRepository.save(account);

        request.logout();
    }

}
