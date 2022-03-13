package org.keelfy.dndlist.service;

import org.keelfy.dndlist.data.Credentials;
import org.keelfy.dndlist.security.model.CredentialsLockType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.ZonedDateTime;

/**
 * @author Egor Kuzmin
 */
public interface CredentialsService extends UserDetailsService {

    Credentials getCredentialsByUsername(String username);

    Credentials getCredentialsByEmail(String email);

    void afterAuthenticationFailure(Credentials credentials);

    void removeTemporalLock();

    void afterAuthenticationAttempt(Credentials credentials, ZonedDateTime attemptAt, boolean successful);

    Credentials setCredentialsLock(Credentials credentials, CredentialsLockType lockType, ZonedDateTime lockedAt);

    @Override
    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getCredentialsByUsername(username);
    }

    default Credentials unlockCredentials(Credentials credentials) {
        return setCredentialsLock(credentials, CredentialsLockType.NONE);
    }

    default Credentials setCredentialsLock(Credentials credentials, CredentialsLockType lockType) {
        return setCredentialsLock(credentials, lockType, ZonedDateTime.now());
    }

}
