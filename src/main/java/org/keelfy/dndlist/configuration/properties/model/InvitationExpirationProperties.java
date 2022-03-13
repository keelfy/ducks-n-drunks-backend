package org.keelfy.dndlist.configuration.properties.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author e.kuzmin
 * */
@Data
@Validated
public class InvitationExpirationProperties {

    /**
     * Время истечения приглашения.
     * */
    @NotNull
    private Integer expirationMinutes;

}
