package org.keelfy.dndlist.data.repository;

import org.keelfy.dndlist.data.CharacterRace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

/**
 * @author Egor Kuzmin
 */
public interface CharacterRaceRepository extends JpaRepository<CharacterRace, BigInteger> {
}
