package org.keelfy.dndlist.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author Egor Kuzmin
 * */
@Getter
@RequiredArgsConstructor
public enum TokenClaimType {

    EMAIL(String.class),

    ROLES(List.class),

    USER_ID(Long.class);

    private final Class<?> objectType;

}
