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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityService implements AuthenticationProvider {
    public static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String accountName = String.valueOf(authentication.getPrincipal());
        final String accountPassword = String.valueOf(authentication.getCredentials());

        final Account account = accountRepository.findByName(accountName);

        if (account != null && PASSWORD_ENCODER.matches(accountPassword, account.getPassword())) {
            final List<GrantedAuthority> grantedAuthorities;
            switch (account.getRole()) {
                case ADMIN: {
                    grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
                    break;
                }
                case USER: {
                    grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
                    break;
                }
                default: {
                    grantedAuthorities = AuthorityUtils.createAuthorityList();
                    break;
                }
            }
            return new UsernamePasswordAuthenticationToken(accountName, accountPassword, grantedAuthorities);
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
