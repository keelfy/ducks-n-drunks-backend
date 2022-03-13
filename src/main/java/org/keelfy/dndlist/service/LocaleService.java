package org.keelfy.dndlist.service;

import org.keelfy.dndlist.data.Locale;
import org.keelfy.dndlist.data.embeddable.LocalizedName;
import org.keelfy.dndlist.model.request.TranslationRequest;

import java.util.Map;

/**
 * @author Egor Kuzmin
 */
public interface LocaleService {

    Map<Locale, LocalizedName> createLocalizedNames(TranslationRequest request);

}
