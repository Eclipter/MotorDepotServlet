package by.bsu.dektiarev.action.util;

import by.bsu.dektiarev.action.*;
import by.bsu.dektiarev.exception.CommandNotFoundException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;

import java.util.EnumMap;
import java.util.Map;

/**
 * Class that helps to get an action class corresponding to the command name
 * Created by USER on 25.04.2016.
 */
public final class CommandHelper {

    private static final Map<ActionEnum, Action> ACTION_MAP = new EnumMap<>(ActionEnum.class);

    static {
        ACTION_MAP.put(ActionEnum.LOGIN, new LoginAction());
        ACTION_MAP.put(ActionEnum.LOGOUT, new LogoutAction());
        ACTION_MAP.put(ActionEnum.GET_TRUCKS, new GetTrucksAction());
        ACTION_MAP.put(ActionEnum.CHANGE_TRUCK_STATE, new ChangeTruckStateAction());
        ACTION_MAP.put(ActionEnum.GET_DRIVERS, new GetDriversAction());
        ACTION_MAP.put(ActionEnum.GET_REQUESTS, new GetRequestsAction());
        ACTION_MAP.put(ActionEnum.GET_TRIPS, new GetTripsAction());
        ACTION_MAP.put(ActionEnum.GET_TRIPS_BY_DRIVER, new GetTripsByDriverAction());
        ACTION_MAP.put(ActionEnum.GET_ASSIGNATION_FORM, new GetAssignationFormAction());
        ACTION_MAP.put(ActionEnum.ASSIGN_DRIVER_TO_A_TRIP, new AssignDriverToATripAction());
        ACTION_MAP.put(ActionEnum.GET_SIGNUP_FORM, new GetSignupFormAction());
        ACTION_MAP.put(ActionEnum.SIGNUP, new SignupAction());
        ACTION_MAP.put(ActionEnum.CHANGE_TRIP_STATE, new ChangeTripStateAction());
        ACTION_MAP.put(ActionEnum.CHAT, new GetChatAction());
        ACTION_MAP.put(ActionEnum.GET_MAIN_PAGE, new GetMainPageAction());
        ACTION_MAP.put(ActionEnum.ADD_REQUEST, new AddRequestAction());
        ACTION_MAP.put(ActionEnum.GET_UNASSIGNED_REQUESTS, new GetUnassignedRequestsAction());
        ACTION_MAP.put(ActionEnum.GET_LOGIN_FORM, new GetLoginFormAction());
        ACTION_MAP.put(ActionEnum.DELETE_REQUEST, new DeleteRequestAction());
    }

    public static Action getCommand(String command) throws CommandNotFoundException {
        ActionEnum action;
        try {
            action = ActionEnum.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new CommandNotFoundException(ExceptionalMessageKey.WRONG_COMMAND);
        }
        return ACTION_MAP.get(action);
    }

    private CommandHelper() {
    }
}
