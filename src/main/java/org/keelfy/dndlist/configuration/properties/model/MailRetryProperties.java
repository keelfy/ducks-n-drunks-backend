package org.keelfy.dndlist.configuration.properties.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author Egor Kuzmin
 * */
@Data
@Validated
public class MailRetryProperties {

    @NotNull
    private Long interval;

    @NotNull
    private Integer maxAttempts;

}
