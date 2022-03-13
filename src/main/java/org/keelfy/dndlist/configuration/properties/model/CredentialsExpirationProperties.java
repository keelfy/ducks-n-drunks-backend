package org.keelfy.dndlist.configuration.properties.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Настройки деактивации учетной записи из-за неактивности.
 *
 * @author e.kuzmin
 */
@Data
@Validated
public class CredentialsExpirationProperties {

    /**
     * Количество дней с момента последнего входа до момента деактивации учетной записи.
     */
    @NotNull
    private Integer expirationDayAmount;

    /**
     * Настройки уведомления администрации об деактивации учетной записи.
     */
    @NotNull
    private CredentialsExpiredMailProperties mail = new CredentialsExpiredMailProperties();

}
