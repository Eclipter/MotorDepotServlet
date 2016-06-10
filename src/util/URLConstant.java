package util;

import action.util.ActionEnum;

/**
 * Created by USER on 10.06.2016.
 */
public final class URLConstant {

    private static final String baseURL = "motor_depot?command=";

    public static final String GET_REQUESTS = baseURL + ActionEnum.GET_REQUESTS.toString().toLowerCase();
    public static final String GET_TRIPS = baseURL + ActionEnum.GET_TRIPS.toString().toLowerCase();
    public static final String GET_TRUCKS = baseURL + ActionEnum.GET_TRUCKS.toString().toLowerCase();
    public static final String GET_INDEX_PAGE = baseURL + ActionEnum.GET_INDEX_PAGE.toString().toLowerCase();
    public static final String GET_SIGNUP_FORM = baseURL + ActionEnum.SIGNUP_FORM.toString().toLowerCase();
    public static final String GET_LOGIN_FORM = baseURL + ActionEnum.GET_LOGIN_FORM.toString().toLowerCase();

    private URLConstant() {
    }
}
