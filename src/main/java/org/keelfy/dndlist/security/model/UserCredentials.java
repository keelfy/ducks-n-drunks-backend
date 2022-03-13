package org.keelfy.dndlist.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Egor Kuzmin
 */
public interface UserCredentials extends UserDetails {

    String getRoles();

    Boolean getActivated();

    CredentialsLockType getLockType();

    @Override
    default Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(getRoles())
                .map(x -> x.split(","))
                .map(x -> Arrays.stream(x).map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

    default Collection<String> getLiteralAuthorities() {
        return Optional.ofNullable(getRoles())
                .map(s -> s.split(","))
                .map(arr -> Arrays.stream(arr).collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

    @Override
    default boolean isAccountNonExpired() {
        return true;
    }

    @Override
    default boolean isAccountNonLocked() {
        return getLockType() == CredentialsLockType.NONE;
    }

    @Override
    default boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    default boolean isEnabled() {
        return getActivated();
    }

}
