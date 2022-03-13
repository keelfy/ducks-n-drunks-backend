package org.keelfy.dndlist.security.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author Egor Kuzmin
 */
public final class AuthenticationFailureBadCredentialsEvent extends ApplicationEvent {

    private static final long serialVersionUID = 7552914695161579531L;

    public AuthenticationFailureBadCredentialsEvent(String username) {
        super(username);
    }

    public String getUsername() {
        return (String) this.source;
    }

}
