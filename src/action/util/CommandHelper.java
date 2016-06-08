package action.util;

import action.*;

import java.util.EnumMap;
import java.util.Map;

/**
 * Class that helps to get an action class corresponding to the command name
 * Created by USER on 25.04.2016.
 */
public final class CommandHelper {

    private static final Map<ActionEnum, Action> actionMap = new EnumMap<>(ActionEnum.class);

    static {
        actionMap.put(ActionEnum.LOGIN, new LoginAction());
        actionMap.put(ActionEnum.LOGOUT, new LogoutAction());
        actionMap.put(ActionEnum.GET_TRUCKS, new GetTrucksAction());
        actionMap.put(ActionEnum.CHANGE_TRUCK_STATE, new ChangeTruckStateAction());
        actionMap.put(ActionEnum.GET_DRIVERS, new GetDriversAction());
        actionMap.put(ActionEnum.GET_REQUESTS, new GetRequestsAction());
        actionMap.put(ActionEnum.GET_TRIPS, new GetTripsAction());
        actionMap.put(ActionEnum.GET_TRIPS_BY_DRIVER, new GetTripsByDriverAction());
        actionMap.put(ActionEnum.GET_SETTING_FORM, new GetSettingFormAction());
        actionMap.put(ActionEnum.SET_DRIVER_ON_TRIP, new SetDriverOnTripAction());
        actionMap.put(ActionEnum.SIGNUP_FORM, new SignupFormAction());
        actionMap.put(ActionEnum.SIGNUP, new SignupAction());
        actionMap.put(ActionEnum.CHANGE_TRIP_STATE, new ChangeTripStateAction());
        actionMap.put(ActionEnum.CHAT, new GetChatAction());
    }

    public static Action getCommand(String command) {
        return actionMap.get(ActionEnum.valueOf(command.toUpperCase()));
    }
}
