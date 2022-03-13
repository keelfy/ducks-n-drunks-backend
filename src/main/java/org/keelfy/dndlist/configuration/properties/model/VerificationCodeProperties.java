package org.keelfy.dndlist.configuration.properties.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author e.kuzmin
 * */
@Data
@Validated
@SuppressWarnings("checkstyle:magicnumber")
public class VerificationCodeProperties {

    /**
     * Время, в течение которого ОТП-код актуален.
     * (5 минут по стандарту)
     * */
    @NotNull
    private Long lifetimeMs = 5 * 60 * 1000L;

}
