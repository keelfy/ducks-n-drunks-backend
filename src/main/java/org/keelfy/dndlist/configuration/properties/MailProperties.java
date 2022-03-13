package org.keelfy.dndlist.configuration.properties;

import lombok.Data;
import org.keelfy.dndlist.configuration.properties.model.MailRetryProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author Egor Kuzmin
 */
@Data
@Validated
@ConfigurationProperties(prefix = "app.mail")
public class MailProperties {

    @NotNull
    private String sender;

    @NotNull
    private String attachmentDirectory;

    private MailRetryProperties retry = new MailRetryProperties();

}
