package org.keelfy.dndlist.service;

import org.keelfy.dndlist.security.PreAuthenticationChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * @author Egor Kuzmin
 */
public interface AuthenticationService {

    AuthenticationProvider createAuthenticationProvider();

    default UserDetailsChecker createPreAuthenticationChecker() {
        return new PreAuthenticationChecker();
    }

}
