package org.keelfy.dndlist.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.keelfy.dndlist.configuration.properties.SecurityProperties;
import org.keelfy.dndlist.security.event.SecurityEventPublisher;
import org.keelfy.dndlist.security.filter.AuthenticationFilter;
import org.keelfy.dndlist.security.filter.AuthorizationFilter;
import org.keelfy.dndlist.security.handler.AuthenticationFailureHandlerImpl;
import org.keelfy.dndlist.security.handler.AuthenticationSuccessHandlerImpl;
import org.keelfy.dndlist.service.AuthenticationService;
import org.keelfy.dndlist.service.CredentialsService;
import org.keelfy.dndlist.service.SecurityService;
import org.keelfy.dndlist.util.RestConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.PostConstruct;

/**
 * @author Egor Kuzmin
 */
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] PERMITTED_GET_REQUESTS = {};

    private static final String[] PERMITTED_POST_REQUESTS = {
            RestConstants.AUTH_LOGIN_URL,
            RestConstants.AUTH_LOGOUT_URL
    };

    private final CredentialsService credentialsService;

    private final AuthenticationService authenticationService;

    private final SecurityService securityService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final SecurityEventPublisher securityEventPublisher;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AccessDeniedHandler accessDeniedHandler;

    private final ObjectMapper objectMapper;

    private final SecurityProperties securityProperties;

    @PostConstruct
    public void postConstruct() throws Exception {
        authenticationManagerBuilder
                .authenticationProvider(authenticationService.createAuthenticationProvider())
                .userDetailsService(credentialsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .csrf().disable()
                .formLogin()
                .loginProcessingUrl(RestConstants.AUTH_LOGIN_URL)
                .usernameParameter(RestConstants.USERNAME_PARAMETER)
                .passwordParameter(RestConstants.PASSWORD_PARAMETER)
                .permitAll()
                .and()
                .logout().logoutUrl(RestConstants.AUTH_LOGOUT_URL)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, PERMITTED_POST_REQUESTS).permitAll()
                .antMatchers(HttpMethod.GET, PERMITTED_GET_REQUESTS).permitAll()
                .and()
                .addFilter(authenticationFilter())
                .addFilter(authorizationFilter())
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }

    private AuthenticationFilter authenticationFilter() throws Exception {
        return new AuthenticationFilter(
                authenticationManager(),
                credentialsService,
                authenticationSuccessHandler(),
                authenticationFailureHandler(),
                securityEventPublisher,
                objectMapper
        );
    }

    private AuthorizationFilter authorizationFilter() throws Exception {
        return new AuthorizationFilter(authenticationManager(), authenticationEntryPoint)
                .setSecurityService(securityService)
                .setSecurityProperties(securityProperties)
                .setObjectMapper(objectMapper);
    }

    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl(
                objectMapper,
                credentialsService,
                securityService,
                securityProperties,
                securityEventPublisher
        );
    }

    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandlerImpl(authenticationEntryPoint);
    }

}
