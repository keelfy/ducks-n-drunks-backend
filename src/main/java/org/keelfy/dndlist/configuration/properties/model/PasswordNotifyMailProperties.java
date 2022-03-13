package org.keelfy.dndlist.configuration.properties.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author e.kuzmin
 * */
@Data
@Validated
public class PasswordNotifyMailProperties implements MailMessagePropertiesProvider {

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
