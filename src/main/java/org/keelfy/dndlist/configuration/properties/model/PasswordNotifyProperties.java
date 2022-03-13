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
public class PasswordNotifyProperties {

    /**
     * За сколько дней до истечения пароля необходимо высылать уведомления.
     * */
    @NotNull
    private Integer notifyWhenDayRemaining = 14;

    /**
     * Настройки электронного письма с уведомлением.
     * */
    private PasswordNotifyMailProperties mail = new PasswordNotifyMailProperties();

}
