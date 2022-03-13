package org.keelfy.dndlist.configuration.properties;

import lombok.Data;
import org.keelfy.dndlist.configuration.properties.model.CredentialsAuthProperties;
import org.keelfy.dndlist.configuration.properties.model.CredentialsExpirationProperties;
import org.keelfy.dndlist.configuration.properties.model.CredentialsPasswordProperties;
import org.keelfy.dndlist.configuration.properties.model.VerificationCodeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author e.kuzmin
 */
@Data
@Validated
@ConfigurationProperties(prefix = "app.credentials")
public class CredentialsProperties {

    /**
     * Настройки входа в учетную запись.
     */
    @NotNull
    private CredentialsAuthProperties auth = new CredentialsAuthProperties();

    /**
     * Настройки деактивации по причине неактивности.
     */
    @NotNull
    private CredentialsExpirationProperties expiration = new CredentialsExpirationProperties();

    /**
     * Настройки пароля и его контроля.
     */
    @NotNull
    private CredentialsPasswordProperties password = new CredentialsPasswordProperties();

    /**
     * Настройки ОТП-кодов.
     */
    @NotNull
    private VerificationCodeProperties verificationCode = new VerificationCodeProperties();

}
