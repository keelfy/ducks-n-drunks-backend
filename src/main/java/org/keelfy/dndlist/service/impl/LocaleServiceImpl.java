package org.keelfy.dndlist.service.impl;

import lombok.RequiredArgsConstructor;
import org.keelfy.dndlist.data.Locale;
import org.keelfy.dndlist.data.embeddable.LocalizedName;
import org.keelfy.dndlist.data.repository.LocaleRepository;
import org.keelfy.dndlist.model.request.TranslationRequest;
import org.keelfy.dndlist.service.LocaleService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Egor Kuzmin
 */
@Service
@RequiredArgsConstructor
public class LocaleServiceImpl implements LocaleService {

    private final LocaleRepository localeRepository;

    @Override
    public Map<Locale, LocalizedName> createLocalizedNames(TranslationRequest request) {
        final var localeIds = localeRepository.findAll()
                .stream()
                .map(Locale::getId)
                .collect(Collectors.toSet());

        return request.getLocales()
                .entrySet()
                .stream()
                .filter(entry -> localeIds.contains(entry.getKey()))
                .collect(Collectors.toMap(
                        entry -> new Locale().setId(entry.getKey()),
                        entry -> new LocalizedName(entry.getValue()))
                );
    }

}
