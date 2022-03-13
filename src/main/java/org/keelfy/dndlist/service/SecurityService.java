package org.keelfy.dndlist.service;


import org.keelfy.dndlist.data.Credentials;
import org.keelfy.dndlist.model.response.RefreshTokenResponse;
import org.keelfy.dndlist.security.model.TokenClaimType;

import java.util.Map;

/**
 * @author Egor Kuzmin
 */
public interface SecurityService {

    Map<TokenClaimType, Object> parseTokenFromRequest(String token);

    String createAccessToken(Credentials credentials);

    String createRefreshToken(Credentials credentials);

    String createResetPasswordToken(Credentials credentials);

    RefreshTokenResponse createAccessByRefreshToken(String refreshToken);

    void checkRefreshToken(String refreshToken);

    void logTokenFailure(Throwable throwable, String token);

}
