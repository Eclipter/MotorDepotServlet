package by.bsu.dektiarev.action.util;

import by.bsu.dektiarev.action.*;
import by.bsu.dektiarev.exception.CommandNotFoundException;
import by.bsu.dektiarev.exception.ExceptionalMessage;

import java.util.EnumMap;
import java.util.Map;

/**
 * Class that helps to get an by.bsu.dektiarev.action class corresponding to the command name
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
        actionMap.put(ActionEnum.GET_SIGNUP_FORM, new GetSignupFormAction());
        actionMap.put(ActionEnum.SIGNUP, new SignupAction());
        actionMap.put(ActionEnum.CHANGE_TRIP_STATE, new ChangeTripStateAction());
        actionMap.put(ActionEnum.CHAT, new GetChatAction());
        actionMap.put(ActionEnum.GET_MAIN_PAGE, new GetMainPageAction());
        actionMap.put(ActionEnum.ADD_REQUEST, new AddRequestAction());
        actionMap.put(ActionEnum.GET_UNASSIGNED_REQUESTS, new GetUnassignedRequestsAction());
        actionMap.put(ActionEnum.GET_LOGIN_FORM, new GetLoginFormAction());
        actionMap.put(ActionEnum.DELETE_REQUEST, new DeleteRequestAction());
    }

    public static Action getCommand(String command) throws CommandNotFoundException {
        ActionEnum action;
        try {
            action = ActionEnum.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new CommandNotFoundException(ExceptionalMessage.WRONG_COMMAND);
        }
        return actionMap.get(action);
    }

    private CommandHelper() {
    }
}
