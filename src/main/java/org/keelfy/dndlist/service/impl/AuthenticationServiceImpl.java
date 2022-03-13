package org.keelfy.dndlist.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keelfy.dndlist.service.AuthenticationService;
import org.keelfy.dndlist.service.CredentialsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Egor Kuzmin
 */
@Slf4j
@Service
@RequiredArgsConstructor
public final class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    private final CredentialsService credentialsService;

    @Override
    public AuthenticationProvider createAuthenticationProvider() {
        final var provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(credentialsService);
        provider.setPreAuthenticationChecks(createPreAuthenticationChecker());
        return provider;
    }

}
