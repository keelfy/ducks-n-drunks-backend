package org.keelfy.dndlist.security.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Egor Kuzmin
 */
@Data
@RequiredArgsConstructor
@Accessors(chain = true)
public class TokenAuthenticationDetails {

    private final Long userId;

}
