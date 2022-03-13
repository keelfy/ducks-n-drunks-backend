package org.keelfy.dndlist.data.repository;

import org.keelfy.dndlist.data.Credentials;
import org.keelfy.dndlist.security.model.CredentialsLockType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Egor Kuzmin
 */
public interface CredentialsRepository extends JpaRepository<Credentials, BigInteger> {

    Optional<Credentials> findByUsername(@NonNull String username);

    Optional<Credentials> findByEmail(@NonNull String email);

    List<Credentials> findByLockTypeAndLockedAtBefore(@NonNull CredentialsLockType credentialsLockType,
                                                      @NonNull ZonedDateTime targetTime);

}
