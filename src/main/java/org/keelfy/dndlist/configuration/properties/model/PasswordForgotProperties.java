package org.keelfy.dndlist.configuration.properties.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author e.kuzmin
 * */
@Data
@Validated
public class PasswordForgotProperties {

    /**
     * Время, которое токен для восстановления пароля будет актуален.
     * */
    @NotNull
    private Long tokenExpirationMinutes;

    /**
     * Настройки письма владельцу учетной записи с информаией о восстановлении.
     * */
    private PasswordForgotMailProperties mail = new PasswordForgotMailProperties();

}
