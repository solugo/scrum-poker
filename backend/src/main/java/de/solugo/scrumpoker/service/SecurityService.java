package de.solugo.scrumpoker.service;

import de.solugo.scrumpoker.data.entity.Account;
import de.solugo.scrumpoker.data.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityService implements AuthenticationProvider, UserDetailsService {
    public static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String email = String.valueOf(authentication.getPrincipal());
        final String password = String.valueOf(authentication.getCredentials());

        final Account account = accountRepository.findByEmail(email);

        if (account != null && SecurityService.PASSWORD_ENCODER.matches(password, account.getPassword())) {
            return new UsernamePasswordAuthenticationToken(account.getEmail(), account.getPassword(), createAuthorities(account));
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Account account = accountRepository.findByEmail(email);
        if (account != null) {
            return new User(email, account.getPassword(), createAuthorities(account));
        } else {
            return null;
        }
    }

    private List<GrantedAuthority> createAuthorities(final Account account) {
        final List<GrantedAuthority> authorities;
        switch (account.getRole()) {
            case ADMIN: {
                authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
                break;
            }
            case USER: {
                authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
                break;
            }
            case TRANSIENT: {
                authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
                break;
            }
            default: {
                authorities = AuthorityUtils.createAuthorityList();
                break;
            }
        }
        return authorities;
    }
}
