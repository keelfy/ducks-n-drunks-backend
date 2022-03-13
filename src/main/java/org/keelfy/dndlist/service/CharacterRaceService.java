package org.keelfy.dndlist.service;

import org.keelfy.dndlist.data.CharacterRace;
import org.keelfy.dndlist.model.request.CreateRaceRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author Egor Kuzmin
 */
public interface CharacterRaceService {

    @PreAuthorize("hasAnyAuthority(T(org.keelfy.dndlist.security.DefaultAuthority).SYSTEM," +
            "T(org.keelfy.dndlist.security.DefaultAuthority).W_CHARACTER_RACE)")
    CharacterRace createRace(CreateRaceRequest request);

    @PreAuthorize("hasAnyAuthority(T(org.keelfy.dndlist.security.DefaultAuthority).DEFAULT," +
            "T(org.keelfy.dndlist.security.DefaultAuthority).R_CHARACTER_RACE)")
    List<CharacterRace> getRaces();

}
