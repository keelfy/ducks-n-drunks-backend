package org.keelfy.dndlist.model.request;

import lombok.Data;
import org.keelfy.dndlist.model.AccessType;

/**
 * @author Egor Kuzmin
 */
@Data
public class CreateRaceRequest {

    private AccessType accessType;

    private TranslationRequest translations = new TranslationRequest();

}
