package org.keelfy.dndlist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Egor Kuzmin
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CredentialsNotFoundException extends UsernameNotFoundException {

    public CredentialsNotFoundException(String message) {
        super(message);
    }

}
