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
        actionMap.put(ActionEnum.GET_ASSIGNATION_FORM, new GetAssignationFormAction());
        actionMap.put(ActionEnum.ASSIGN_DRIVER_TO_A_TRIP, new AssignDriverToATripAction());
        actionMap.put(ActionEnum.SIGNUP_FORM, new SignupFormAction());
        actionMap.put(ActionEnum.SIGNUP, new SignupAction());
        actionMap.put(ActionEnum.CHANGE_TRIP_STATE, new ChangeTripStateAction());
        actionMap.put(ActionEnum.CHAT, new GetChatAction());
        actionMap.put(ActionEnum.GET_INDEX_PAGE, new GetIndexPageAction());
        actionMap.put(ActionEnum.ADD_REQUEST, new AddRequestAction());
        actionMap.put(ActionEnum.GET_UNASSIGNED_REQUESTS, new GetUnassignedRequestsAction());
        actionMap.put(ActionEnum.GET_LOGIN_FORM, new GetLoginFormAction());
    }

    public static Action getCommand(String command) {
        return actionMap.get(ActionEnum.valueOf(command.toUpperCase()));
    }

    private CommandHelper() {
    }
}
