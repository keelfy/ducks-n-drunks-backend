package org.keelfy.dndlist.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author e.kuzmin
 */
@Data
@Accessors(chain = true)
public class ErrorResponse {

    private Integer code;

    private String message;

}
