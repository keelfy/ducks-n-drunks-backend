package org.keelfy.dndlist.security;

import lombok.extern.slf4j.Slf4j;
import org.keelfy.dndlist.data.Credentials;
import org.keelfy.dndlist.security.model.CredentialsLockType;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * @author Egor Kuzmin
 */
@Slf4j
public class PreAuthenticationChecker implements UserDetailsChecker {

    @Override
    public void check(UserDetails user) {
        if (!(user instanceof Credentials)) {
            throw new IllegalArgumentException("UserDetails must be UserCredentialDetails");
        }

        final var credentials = (Credentials) user;
        final var lockType = credentials.getLockType();
        final var enabled = user.isEnabled();

        checkBlockType(lockType);
        checkEnabled(enabled);
    }

    protected void checkBlockType(CredentialsLockType lockType) {
        if (lockType == CredentialsLockType.TEMPORAL) {
            log.debug("Account temporarily blocked: Too much failed login attempts");
            throw new LockedException("Account temporarily blocked: Too much failed login attempts");
        } else if (lockType != CredentialsLockType.NONE) {
            log.debug("Account blocked [" + lockType + "]");
            throw new LockedException("Account blocked");
        }
    }

    protected void checkEnabled(boolean enabled) {
        if (!enabled) {
            log.debug("User account is disabled");
            throw new DisabledException("User is disabled");
        }
    }

}