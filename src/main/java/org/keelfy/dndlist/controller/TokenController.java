package org.keelfy.dndlist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keelfy.dndlist.model.request.RefreshTokenRequest;
import org.keelfy.dndlist.model.response.RefreshTokenResponse;
import org.keelfy.dndlist.service.SecurityService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Egor Kuzmin
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TokenController implements RootController {

    private static final String CONTROLLER_MAPPING = API_V1_MAPPING + "/token";

    public static final String REFRESH_TOKEN_MAPPING = CONTROLLER_MAPPING + "/refresh";

    private final SecurityService securityService;

    @PostMapping(value = REFRESH_TOKEN_MAPPING,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RefreshTokenResponse refreshToken(@Validated @RequestBody RefreshTokenRequest request) {
        final var refreshToken = request.getRefreshToken();
        return securityService.createAccessByRefreshToken(refreshToken);
    }

}