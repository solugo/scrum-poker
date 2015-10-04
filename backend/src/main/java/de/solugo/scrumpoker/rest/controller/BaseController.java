package de.solugo.scrumpoker.rest.controller;

import de.solugo.scrumpoker.data.entity.Account;
import de.solugo.scrumpoker.data.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseController {

    @Autowired
    private AccountRepository accountRepository;

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Void> handleEmptyResultDataAccessException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Void> handleAuthenticationCredentialsNotFoundException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    protected Account getCurrentAccount() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return accountRepository.findByEmail(authentication.getName());
        } else {
            return null;
        }
    }

    protected Long getCurrentAccountId() {
        final Account account = this.getCurrentAccount();
        if (account != null) {
            return account.getId();
        } else {
            return null;
        }
    }
}
