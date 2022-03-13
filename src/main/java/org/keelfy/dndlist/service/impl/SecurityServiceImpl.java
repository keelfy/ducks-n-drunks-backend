package org.keelfy.dndlist.service.impl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keelfy.dndlist.configuration.properties.CredentialsProperties;
import org.keelfy.dndlist.configuration.properties.SecurityProperties;
import org.keelfy.dndlist.data.Credentials;
import org.keelfy.dndlist.model.response.RefreshTokenResponse;
import org.keelfy.dndlist.security.model.TokenClaimType;
import org.keelfy.dndlist.service.CredentialsService;
import org.keelfy.dndlist.service.SecurityService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @author e.kuzmin
 */
@Slf4j
@Service
@RequiredArgsConstructor
public final class SecurityServiceImpl implements SecurityService {

    private static final Long MILLIS_MULTIPLIER = 60L * 1000L;

    private final CredentialsService credentialsService;

    private final SecurityProperties securityProperties;

    private final CredentialsProperties credentialsProperties;

    @Override
    public Map<TokenClaimType, Object> parseTokenFromRequest(String token) {
        final var claims = Jwts.parser()
                .setSigningKey(getSecret())
                .parseClaimsJws(token.replace(securityProperties.getTokenPrefix(), ""));

        return Map.of(
                TokenClaimType.EMAIL, claims.getBody().getSubject(),
                TokenClaimType.ROLES, claims.getBody().get(TokenClaimType.ROLES.toString()),
                TokenClaimType.USER_ID, claims.getBody().get(TokenClaimType.USER_ID.toString())
        );
    }

    @Override
    public String createAccessToken(Credentials credentials) {
        final var authorities = credentials.getLiteralAuthorities();
        final var expirationTime = securityProperties.getAccessTokenExpirationMinutes() * MILLIS_MULTIPLIER;
        return createToken(credentials, authorities, expirationTime);
    }

    @Override
    public String createRefreshToken(Credentials credentials) {
        final var authorities = credentials.getLiteralAuthorities();
        final var expirationTime = securityProperties.getRefreshTokenExpirationMinutes() * MILLIS_MULTIPLIER;
        return createToken(credentials, authorities, expirationTime);
    }

    private String createToken(Credentials credentials, Collection<String> roles, long expirationTime) {
        return builder(credentials.getEmail(), expirationTime)
                .claim(TokenClaimType.ROLES.toString(), roles)
                .claim(TokenClaimType.USER_ID.toString(), credentials.getId())
                .compact();
    }

    @Override
    public String createResetPasswordToken(Credentials credentials) {
        final var properties = credentialsProperties.getPassword().getForgot();
        final var expirationTime = properties.getTokenExpirationMinutes() * MILLIS_MULTIPLIER;
        return builder(credentials.getEmail(), expirationTime)
                .claim(TokenClaimType.USER_ID.toString(), credentials.getId())
                .compact();
    }

    private JwtBuilder builder(String email, Long expirationTime) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, getSecret())
                .setHeaderParam(securityProperties.getTokenTypeHeader(), securityProperties.getTokenType())
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime));
    }

    private byte[] getSecret() {
        return securityProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public RefreshTokenResponse createAccessByRefreshToken(String refreshToken) {
        checkRefreshToken(refreshToken);

        try {
            final var parsedRefreshToken = parseTokenFromRequest(refreshToken);
            final var email = (String) parsedRefreshToken.get(TokenClaimType.EMAIL);
            final var credentials = credentialsService.getCredentialsByEmail(email);
            final var newAccessToken = createAccessToken(credentials);
            final var newRefreshToken = createRefreshToken(credentials);
            return new RefreshTokenResponse(newAccessToken, newRefreshToken);
        } catch (Exception ex) {
            logTokenFailure(ex, refreshToken);
            throw ex;
        }
    }

    @Override
    public void logTokenFailure(Throwable throwable, String token) {
        final var builder = new StringBuilder();

        if (throwable instanceof ExpiredJwtException) {
            builder.append("Request to parse expired JWT : %s failed : %s");
        } else if (throwable instanceof UnsupportedJwtException) {
            builder.append("Request to parse unsupported JWT : %s failed : %s");
        } else if (throwable instanceof MalformedJwtException) {
            builder.append("Request to parse invalid JWT : %s failed : %s");
        } else if (throwable instanceof SignatureException) {
            builder.append("Request to parse JWT with invalid signature : %s failed : %s");
        } else if (throwable instanceof IllegalArgumentException) {
            builder.append("Request to parse empty or null JWT : %s failed : %s");
        } else {
            builder.append("Request to parse JWT failed. Token: %s : Error: %s");
        }

        final var message = String.format(builder.toString(), token, throwable.getMessage());
        log.debug(message);
    }

    @Override
    public void checkRefreshToken(String refreshToken) {
        if (StringUtils.hasText(refreshToken)) {
            throw new IllegalArgumentException("Refresh token cannot be empty");
        }
    }

}
