package org.keelfy.dndlist.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Egor Kuzmin
 */
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException authenticationException)
            throws IOException, ServletException {

        authenticationEntryPoint.commence(request, response, authenticationException);
    }

}
