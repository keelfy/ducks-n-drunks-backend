package org.keelfy.dndlist.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Egor Kuzmin
 * */
@Data
@Accessors(chain = true)
public class CredentialsDto {

    private String email;

    private String username;

    private String password;

}