package org.keelfy.dndlist.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keelfy.dndlist.service.CredentialsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Egor Kuzmin
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CredentialsScheduler {

    private final CredentialsService credentialsService;

    @Scheduled(cron = "${app.credentials.expiration.temporarily-lock-cron}")
    public void removeTemporalCredentialsLock() {
        credentialsService.removeTemporalLock();
    }

}
