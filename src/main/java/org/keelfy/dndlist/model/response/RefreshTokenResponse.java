package org.keelfy.dndlist.model.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Egor Kuzmin
 */
@Data
@RequiredArgsConstructor
public class RefreshTokenResponse {

    private final String accessToken;

    private final String refreshToken;

}
