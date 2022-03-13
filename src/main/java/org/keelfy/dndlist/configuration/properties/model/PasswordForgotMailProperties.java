package org.keelfy.dndlist.configuration.properties.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author e.kuzmin
 * */
@Data
@Validated
public class PasswordForgotMailProperties implements MailMessagePropertiesProvider {

    /**
     * Ссылка на страницу восстановления пароля, где %s - токен.
     * */
    @NotNull
    private String resetUrl;

    /**
     * Название переменной, представляющий в шаблоне значение {@link #resetUrl}.
     * */
    @NotNull
    private String resetUrlVariableName = "resetPasswordUrl";

    /**
     * Тема письма.
     * */
    @NotNull
    private String subject;

    /**
     * Название файла с шаблоном.
     * */
    @NotNull
    private String messageTemplateName;

}
