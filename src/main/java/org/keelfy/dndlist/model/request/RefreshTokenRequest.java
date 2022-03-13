package org.keelfy.dndlist.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Egor Kuzmin
 */
@Data
public class RefreshTokenRequest {

    @NotEmpty
    private String refreshToken;

}
