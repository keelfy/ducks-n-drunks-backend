package org.keelfy.dndlist.service.impl;

import lombok.RequiredArgsConstructor;
import org.keelfy.dndlist.data.CharacterRace;
import org.keelfy.dndlist.data.repository.CharacterRaceRepository;
import org.keelfy.dndlist.model.request.CreateRaceRequest;
import org.keelfy.dndlist.service.CharacterRaceService;
import org.keelfy.dndlist.service.LocaleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Egor Kuzmin
 */
@Service
@RequiredArgsConstructor
public class CharacterRaceServiceImpl implements CharacterRaceService {

    private final CharacterRaceRepository raceRepository;

    private final LocaleService localeService;

    @Override
    public CharacterRace createRace(CreateRaceRequest request) {
        final var translations = localeService.createLocalizedNames(request.getTranslations());
        final var race = new CharacterRace()
                .setTranslations(translations);
        return raceRepository.saveAndFlush(race);
    }

    @Override
    public List<CharacterRace> getRaces() {
        return raceRepository.findAll();
    }

}
