package by.bsu.dektiarev.dao.util;

/**
 * Class with all database queries used in DAO classes when JDBC mode is enabled
 * Created by USER on 15.06.2016.
 */
public final class DatabaseQuery {

    public static final String GET_ALL_DRIVERS = "SELECT\n" +
            "  driver.USER_ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD,\n" +
            "  driver.TRUCK_ID,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME\n" +
            "FROM driver\n" +
            "  JOIN user ON driver.USER_ID = user.ID\n" +
            "  JOIN truck ON driver.TRUCK_ID = truck.ID\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID";

    public static final String GET_DRIVERS_WITH_HEALTHY_TRUCKS = "SELECT\n" +
            "  driver.USER_ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD,\n" +
            "  driver.TRUCK_ID,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME\n" +
            "FROM driver\n" +
            "  JOIN user ON driver.USER_ID = user.ID\n" +
            "  JOIN truck ON driver.TRUCK_ID = truck.ID\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID\n" +
            "WHERE STATE_ID = 1";

    public static final String GET_DRIVER_BY_USER = "SELECT\n" +
            "  driver.USER_ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD,\n" +
            "  driver.TRUCK_ID,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME\n" +
            "FROM driver\n" +
            "  JOIN user ON driver.USER_ID = user.ID\n" +
            "  JOIN truck ON driver.TRUCK_ID = truck.ID\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID\n" +
            "WHERE USER_ID = ?";

    public static final String INSERT_DRIVER = "INSERT INTO driver VALUES (?, ?)";

    public static final String GET_ALL_REQUSTS = "SELECT\n" +
            "  request.ID,\n" +
            "  request.CARGO_WEIGHT\n" +
            "FROM request";

    public static final String GET_UNASSIGNED_REQUESTS = "SELECT\n" +
            "  request.ID,\n" +
            "  request.CARGO_WEIGHT\n" +
            "FROM request\n" +
            "WHERE request.ID NOT IN (SELECT trip.REQUEST_ID\n" +
            "                         FROM trip)";

    public static final String GET_DRIVER_BY_REQUEST = "SELECT\n" +
            "  driver.USER_ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD,\n" +
            "  driver.TRUCK_ID,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME\n" +
            "FROM driver\n" +
            "  JOIN user ON driver.USER_ID = user.ID\n" +
            "  JOIN truck ON driver.TRUCK_ID = truck.ID\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID\n" +
            "WHERE driver.USER_ID = (SELECT trip.DRIVER_USER_ID\n" +
            "                        FROM trip\n" +
            "                        WHERE trip.REQUEST_ID = ?)";

    public static final String INSERT_REQUEST = "INSERT INTO request (CARGO_WEIGHT) VALUES (?)";

    public static final String GET_ALL_TRIPS = "SELECT\n" +
            "  trip.ID,\n" +
            "  trip.REQUEST_ID,\n" +
            "  request.CARGO_WEIGHT,\n" +
            "  trip.DRIVER_USER_ID AS USER_ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD,\n" +
            "  driver.TRUCK_ID,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME,\n" +
            "  trip.IS_COMPLETE\n" +
            "FROM trip\n" +
            "  JOIN request ON trip.REQUEST_ID = request.ID\n" +
            "  JOIN driver ON trip.DRIVER_USER_ID = driver.USER_ID\n" +
            "  JOIN user ON driver.USER_ID = user.ID\n" +
            "  JOIN truck ON driver.TRUCK_ID = truck.ID\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID";

    public static final String GET_TRIPS_BY_DRIVER = "SELECT\n" +
            "  trip.ID,\n" +
            "  trip.REQUEST_ID,\n" +
            "  request.CARGO_WEIGHT,\n" +
            "  trip.DRIVER_USER_ID AS USER_ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD,\n" +
            "  driver.TRUCK_ID,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME,\n" +
            "  trip.IS_COMPLETE\n" +
            "FROM trip\n" +
            "  JOIN request ON trip.REQUEST_ID = request.ID\n" +
            "  JOIN driver ON trip.DRIVER_USER_ID = driver.USER_ID\n" +
            "  JOIN user ON driver.USER_ID = user.ID\n" +
            "  JOIN truck ON driver.TRUCK_ID = truck.ID\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID\n" +
            "WHERE trip.DRIVER_USER_ID = ?";

    public static final String GET_REQUEST_BY_ID = "SELECT\n" +
            "  request.ID,\n" +
            "  request.CARGO_WEIGHT\n" +
            "FROM request\n" +
            "WHERE request.ID = ?";

    public static final String GET_TRUCK_BY_DRIVER_ID = "SELECT\n" +
            "  truck.ID,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME\n" +
            "FROM driver\n" +
            "  JOIN truck ON driver.TRUCK_ID = truck.ID\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID\n" +
            "WHERE driver.USER_ID = ?";

    public static final String INSERT_TRIP = "INSERT INTO trip " +
            "(REQUEST_ID, DRIVER_USER_ID, IS_COMPLETE) VALUES (?, ?, FALSE)";

    public static final String GET_TRIP_BY_ID = "SELECT\n" +
            "  trip.ID,\n" +
            "  trip.REQUEST_ID,\n" +
            "  request.CARGO_WEIGHT,\n" +
            "  trip.DRIVER_USER_ID AS USER_ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD,\n" +
            "  driver.TRUCK_ID,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME,\n" +
            "  trip.IS_COMPLETE\n" +
            "FROM trip\n" +
            "  JOIN request ON trip.REQUEST_ID = request.ID\n" +
            "  JOIN driver ON trip.DRIVER_USER_ID = driver.USER_ID\n" +
            "  JOIN user ON driver.USER_ID = user.ID\n" +
            "  JOIN truck ON driver.TRUCK_ID = truck.ID\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID\n" +
            "WHERE trip.ID = ?";

    public static final String CHANGE_TRIP_STATE = "UPDATE trip\n" +
            "SET trip.IS_COMPLETE = ?\n" +
            "WHERE trip.ID = ?";

    public static final String GET_ALL_TRUCKS = "SELECT\n" +
            "  truck.ID,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME\n" +
            "FROM truck\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID";

    public static final String GET_TRUCK_BY_ID = "SELECT\n" +
            "  truck.ID,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME\n" +
            "FROM truck\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID\n" +
            "WHERE truck.ID = ?";

    public static final String CHANGE_TRUCK_STATE = "UPDATE truck\n" +
            "SET truck.STATE_ID = ?\n" +
            "WHERE truck.ID = ?";

    public static final String INSERT_TRUCK = "INSERT INTO truck (CAPACITY, STATE_ID) VALUES (?, 1)";

    public static final String GET_USER_BY_LOGIN = "SELECT\n" +
            "  user.ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD\n" +
            "FROM user\n" +
            "WHERE user.LOGIN = ?";

    public static final String GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT\n" +
            "  user.ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD\n" +
            "FROM user\n" +
            "WHERE user.LOGIN = ? AND user.PASSWORD = ?";

    public static final String INSERT_USER = "INSERT INTO user (LOGIN, PASSWORD) VALUES (?, ?)";

    public static final String GET_USER_BY_ID = "SELECT\n" +
            "  user.ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD\n" +
            "FROM user\n" +
            "WHERE user.ID = ?";

    private DatabaseQuery() {
    }
}
