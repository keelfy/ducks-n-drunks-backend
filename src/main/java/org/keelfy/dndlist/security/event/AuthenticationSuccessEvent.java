package org.keelfy.dndlist.security.event;

import lombok.Getter;
import org.keelfy.dndlist.data.Credentials;
import org.springframework.context.ApplicationEvent;

import java.time.ZonedDateTime;

/**
 * @author Egor Kuzmin
 */
@Getter
public final class AuthenticationSuccessEvent extends ApplicationEvent {

    private static final long serialVersionUID = -1218492827523227793L;

    private final ZonedDateTime authenticationTime;

    public AuthenticationSuccessEvent(Credentials credentials, ZonedDateTime authTime) {
        super(credentials);
        this.authenticationTime = authTime;
    }

    public Credentials getCredentials() {
        return (Credentials) this.source;
    }

}
