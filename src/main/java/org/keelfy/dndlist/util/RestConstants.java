package org.keelfy.dndlist.util;

import lombok.experimental.UtilityClass;

/**
 * @author Egor Kuzmin
 */
@UtilityClass
public class RestConstants {

    private static final String API_ROOT_MAPPING = "/api";

    public static final String API_V1_ROOT_MAPPING = API_ROOT_MAPPING + "/v1";

    public static final String AUTH_LOGIN_URL = API_V1_ROOT_MAPPING + "/login";

    public static final String AUTH_LOGOUT_URL = API_V1_ROOT_MAPPING + "/logout";

    public static final String USERNAME_PARAMETER = "email";

    public static final String PASSWORD_PARAMETER = "password";

}
