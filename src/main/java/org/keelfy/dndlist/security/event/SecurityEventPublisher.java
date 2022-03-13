package org.keelfy.dndlist.security.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keelfy.dndlist.data.Credentials;
import org.keelfy.dndlist.service.CredentialsService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;

/**
 * @author e.kuzmin
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    private final CredentialsService credentialsService;

    public void fireAuthenticationFailureBadCredentials(String username) {
        log.info("Publishing authentication failure (bad credentials) event");
        final var event = new AuthenticationFailureBadCredentialsEvent(username);
        eventPublisher.publishEvent(event);
    }

    public void fireAuthenticationSuccess(Credentials credentials, ZonedDateTime authenticationTime) {
        log.info("Publishing authentication success event");
        final var event = new AuthenticationSuccessEvent(credentials, authenticationTime);
        eventPublisher.publishEvent(event);
    }

    @Bean
    ApplicationListener<AuthenticationFailureBadCredentialsEvent> authenticationBadCredentialsListener() {
        return (event) -> {
            final var username = event.getUsername();
            final var credentials = credentialsService.getCredentialsByUsername(username);
            credentialsService.afterAuthenticationFailure(credentials);
        };
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> authenticationSuccessListener() {
        return (event) -> {
            final var credentials = event.getCredentials();
            final var authenticationTime = event.getAuthenticationTime();
            credentialsService.afterAuthenticationAttempt(credentials, authenticationTime, true);
        };
    }

}
