package org.keelfy.dndlist.configuration.properties.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Настройки паролей и их контроля.
 *
 * @author e.kuzmin
 * */
@Data
@Validated
@SuppressWarnings("checkstyle:magicnumber")
public class CredentialsPasswordProperties {

    /**
     * Количество дней, через которое необходимо сменить пароль.
     * */
    @NotNull
    private Integer expirationDayAmount;

    /**
     * Regexp для валидации пароля по критериям.
     *
     * ^                 # начало строки
     * (?=.*[0-9])       # число должно встречаться хотя бы раз
     * (?=.*[a-z])       # буква в нижнем регистре должна присутствовать хотя бы раз
     * (?=.*[A-Z])       # буква в верхнем регистре должна присутствовать хотя бы раз
     * (?=.*[@#$%^&+=])  # особый символ должен присутствовать хотя бы раз
     * (?=\S+$)          # пробелы запрещены во всей строке
     * $                 # конец строки
     * */
    @NotNull
    private String validationRegexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$)$";

    /**
     * Минимальная длина.
     * */
    @NotNull
    private Integer minLength = 10;

    /**
     * Максимальная длина.
     * */
    @NotNull
    private Integer maxLength = 50;

    /**
     * Настройки восставноления пароля.
     * */
    private PasswordForgotProperties forgot = new PasswordForgotProperties();

    /**
     * Настройки уведомления о необходимости смены пароля.
     * */
    private PasswordNotifyProperties notify = new PasswordNotifyProperties();

}
