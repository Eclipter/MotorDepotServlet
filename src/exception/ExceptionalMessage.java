package exception;

/**
 * Created by USER on 15.02.2016.
 */
public class ExceptionalMessage {
    public static final String MISSING_APPLICATION = "No such application.";
    public static final String MISSING_DRIVER = "No such driver.";
    public static final String MISSING_TRUCK = "No such TRUCK.";
    public static final String WEIGHT_MORE_THAN_CAPACITY = "Cargo weight is more than auto capacity.";
    public static final String TRUCK_NOT_OK = "The TRUCK state is not OK.";
    public static final String TRUCK_HAS_THE_SAME_STATE = "TRUCK already has this state.";
    public static final String SQL_EXCEPTION = "Error while operating on database: ";
    public static final String TRUCK_OK = "TRUCK is already OK.";
    public static final String TRUCK_BROKEN = "TRUCK is already broken.";
    public static final String WRONG_LOGIN_PASS = "Wrong login/password.";
    public static final String PASSWORDS_NOT_EQUAL = "Passwords must be equal";
    public static final String LOGIN_OCCUPIED = "There is a user with such login, please choose another";
    public static final String TRUCK_CAPACITY_BELOW_ZERO = "Truck capacity can't be below zero";
    public static final String TRIP_HAS_THIS_STATE = "This trip already has this state.";
    public static final String WRONG_INPUT_FOR_CAPACITY = "Wrong input for capacity";
}
