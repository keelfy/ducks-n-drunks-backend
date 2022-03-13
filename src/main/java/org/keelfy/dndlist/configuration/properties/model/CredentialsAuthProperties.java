package org.keelfy.dndlist.configuration.properties.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author e.kuzmin
 * */
@Data
@Validated
@SuppressWarnings("checkstyle:magicnumber")
public class CredentialsAuthProperties {

    /**
     * Количество попыток аутентификации, по достижению которых учетная запись будет временно заблокирована.
     * */
    @NotNull
    private Integer attemptsToTemporarilyLock = 3;

    /**
     * Включительное количество временных блокировок, по достижению которых будет выдана блокировка банком.
     * */
    @NotNull
    private Integer temporarilyLocksToPermanentlyLock = 3;

    /**
     * Время в минутах, через которое временно-заблокированная учетная запись будет вновь доступна.
     * */
    @NotNull
    private Long temporarilyLockMinutes = 30L;

}
