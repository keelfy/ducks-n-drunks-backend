package org.keelfy.dndlist.configuration.properties.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author e.kuzmin
 */
@Data
@Validated
public class CredentialsExpiredMailProperties implements MailMessagePropertiesProvider {

    /**
     * Тема письма.
     */
    @NotNull
    private String subject;

    /**
     * Электронная почта группы администраторов.
     */
    @NotNull
    private String administrationGroupEmail;

    /**
     * Название переменной в шаблоне, содержащей почту деактивированной учетной записи.
     */
    @NotNull
    private String usernameVariableName = "email";

    /**
     * Название файла шаблона письма.
     */
    @NotNull
    private String messageTemplateName;

}
