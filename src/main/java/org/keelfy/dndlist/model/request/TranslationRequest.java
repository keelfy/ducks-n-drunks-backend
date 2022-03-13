package org.keelfy.dndlist.model.request;

import lombok.Data;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Egor Kuzmin
 */
@Data
public class TranslationRequest {

    private Map<BigInteger, String> locales = new HashMap<>();

}
