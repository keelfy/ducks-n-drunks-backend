package org.keelfy.dndlist.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keelfy.dndlist.exception.CredentialsNotFoundException;
import org.keelfy.dndlist.model.CredentialsDto;
import org.keelfy.dndlist.security.event.SecurityEventPublisher;
import org.keelfy.dndlist.service.CredentialsService;
import org.keelfy.dndlist.util.RestConstants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author e.kuzmin
 */
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final CredentialsService credentialsService;

    private final ObjectMapper mapper;

    private final SecurityEventPublisher securityEventPublisher;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                CredentialsService userCredentialDetailsService,
                                AuthenticationSuccessHandler successHandler,
                                AuthenticationFailureHandler failureHandler,
                                SecurityEventPublisher securityEventPublisher,
                                ObjectMapper objectMapper) {

        this.setAuthenticationManager(authenticationManager);
        this.setFilterProcessesUrl(RestConstants.AUTH_LOGIN_URL);
        this.setAuthenticationSuccessHandler(successHandler);
        this.setAuthenticationFailureHandler(failureHandler);

        this.securityEventPublisher = securityEventPublisher;
        this.credentialsService = userCredentialDetailsService;
        this.mapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            final var credentials = mapper.readValue(request.getInputStream(), CredentialsDto.class);
            final var email = credentials.getEmail();

            try {
                final var user = credentialsService.getCredentialsByEmail(email);
                final var token = new UsernamePasswordAuthenticationToken(
                        credentials.getEmail(),
                        credentials.getPassword(),
                        user.getAuthorities()
                );
                return getAuthenticationManager().authenticate(token);
            } catch (CredentialsNotFoundException | BadCredentialsException ex) {
                securityEventPublisher.fireAuthenticationFailureBadCredentials(email);
                throw ex;
            }
        } catch (CredentialsNotFoundException ex) {
            throw new BadCredentialsException(ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

}
