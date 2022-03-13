package org.keelfy.dndlist.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keelfy.dndlist.configuration.properties.SecurityProperties;
import org.keelfy.dndlist.data.Credentials;
import org.keelfy.dndlist.security.event.SecurityEventPublisher;
import org.keelfy.dndlist.service.CredentialsService;
import org.keelfy.dndlist.service.SecurityService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * @author Egor Kuzmin
 */
@Slf4j
@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    private final CredentialsService credentialsService;

    private final SecurityService jwtService;

    private final SecurityProperties securityProperties;

    private final SecurityEventPublisher securityEventPublisher;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        final var userDetails = (Credentials) authentication.getPrincipal();
        final var username = userDetails.getUsername();
        final var credentials = credentialsService.getCredentialsByUsername(username);

        securityEventPublisher.fireAuthenticationSuccess(credentials, ZonedDateTime.now());

        final var token = jwtService.createAccessToken(credentials);
        final var refreshToken = jwtService.createRefreshToken(credentials);
        final var tokens = Map.of(
                "Authorization", securityProperties.getTokenPrefix() + token,
                "RefreshAuthorization", securityProperties.getTokenPrefix() + refreshToken
        );

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), tokens);
    }

}
