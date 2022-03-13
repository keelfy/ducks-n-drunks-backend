package org.keelfy.dndlist.data.repository;

import org.keelfy.dndlist.data.Locale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

/**
 * @author Egor Kuzmin
 */
public interface LocaleRepository extends JpaRepository<Locale, BigInteger> {
}
