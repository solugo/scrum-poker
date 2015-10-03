package de.solugo.scrumpoker.rest.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseController {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Void> handleEmptyResultDataAccessException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Void> handleAuthenticationCredentialsNotFoundException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
