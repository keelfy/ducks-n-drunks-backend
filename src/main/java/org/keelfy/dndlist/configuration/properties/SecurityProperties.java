package org.keelfy.dndlist.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author e.kuzmin
 */
@Data
@ConfigurationProperties("app.security")
@SuppressWarnings("checkstyle:magicnumber")
public class SecurityProperties {

    private String jwtSecret;

    private String tokenType = "JWT";

    private String tokenTypeHeader = "Token-type";

    private String accessTokenHeader = "Authorization";

    private String tokenPrefix = "Bearer ";

    private String tokenExpiredAttributeName = "expired";

    private Long accessTokenExpirationMinutes = 30L;

    private Long refreshTokenExpirationMinutes = 60L;

}
