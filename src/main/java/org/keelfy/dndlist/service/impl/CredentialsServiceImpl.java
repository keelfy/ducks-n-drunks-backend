package org.keelfy.dndlist.service.impl;

import lombok.RequiredArgsConstructor;
import org.keelfy.dndlist.configuration.properties.CredentialsProperties;
import org.keelfy.dndlist.data.Credentials;
import org.keelfy.dndlist.data.repository.CredentialsRepository;
import org.keelfy.dndlist.exception.CredentialsNotFoundException;
import org.keelfy.dndlist.security.model.CredentialsLockType;
import org.keelfy.dndlist.service.CredentialsService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Egor Kuzmin
 */
@Service
@RequiredArgsConstructor
public class CredentialsServiceImpl implements CredentialsService {

    private final CredentialsRepository credentialsRepository;

    private final CredentialsProperties credentialsProperties;

    @Override
    public Credentials getCredentialsByUsername(String username) {
        return credentialsRepository.findByUsername(username)
                .orElseThrow(() -> new CredentialsNotFoundException("Credentials by username=" + username + " not found"));
    }

    @Override
    public Credentials getCredentialsByEmail(String email) {
        return credentialsRepository.findByEmail(email)
                .orElseThrow(() -> new CredentialsNotFoundException("Credentials by email=" + email + " not found"));
    }

    @Override
    public void afterAuthenticationFailure(Credentials credentials) {
        credentialsRepository.findById(credentials.getId())
                .map(this::afterAuthenticationAttempt)
                .map(this::setTemporalLock)
                .ifPresent(credentialsRepository::saveAndFlush);
    }

    private Credentials afterAuthenticationAttempt(Credentials credentials) {
        return credentials
                .setLatestAuthenticationAttemptAt(ZonedDateTime.now())
                .setAuthenticationAttemptCount(credentials.getAuthenticationAttemptCount() + 1);
    }

    private Credentials setTemporalLock(Credentials credentials) {
        final var blockType = credentials.getLockType();

        if (blockType == CredentialsLockType.NONE) {
            final var properties = credentialsProperties.getAuth();
            final var attemptCounter = credentials.getAuthenticationAttemptCount();
            final var temporarilyLocksAmount = attemptCounter / properties.getAttemptsToTemporarilyLock();

            if (temporarilyLocksAmount >= properties.getTemporarilyLocksToPermanentlyLock()) {
                return setCredentialsLock(credentials, CredentialsLockType.FULL);
            } else if (attemptCounter % properties.getAttemptsToTemporarilyLock() == 0) {
                return setCredentialsLock(credentials, CredentialsLockType.TEMPORAL);
            }
        }

        return credentials;
    }

    @Override
    public void removeTemporalLock() {
        final var lockTime = credentialsProperties.getAuth().getTemporarilyLockMinutes();
        final var targetTime = ZonedDateTime.now().minus(lockTime, ChronoUnit.MINUTES);
        credentialsRepository.findByLockTypeAndLockedAtBefore(CredentialsLockType.TEMPORAL, targetTime)
                .stream()
                .map(this::unlockCredentials)
                .forEach(credentialsRepository::saveAndFlush);
    }

    @Override
    public void afterAuthenticationAttempt(Credentials credentials, ZonedDateTime attemptAt, boolean successful) {
        credentialsRepository.findById(credentials.getId())
                .map(c -> c
                        .setLatestSuccessfulAuthenticationAt(attemptAt)
                        .setLatestAuthenticationAttemptAt(successful ? attemptAt : c.getLatestAuthenticationAttemptAt())
                        .setAuthenticationAttemptCount(0))
                .ifPresent(credentialsRepository::saveAndFlush);
    }

    @Override
    public Credentials setCredentialsLock(Credentials credentials, CredentialsLockType lockType, ZonedDateTime lockedAt) {
        return credentials
                .setLockType(lockType)
                .setLockedAt(lockedAt);
    }

}
