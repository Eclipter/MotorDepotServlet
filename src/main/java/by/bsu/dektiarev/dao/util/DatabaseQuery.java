package by.bsu.dektiarev.dao.util;

/**
 * Class with all database queries used in DAO classes when JDBC mode is enabled
 * Created by USER on 15.06.2016.
 */
public final class DatabaseQuery {

    public static final String GET_ALL_DRIVERS_LIMITED = "SELECT\n" +
            "  driver.USER_ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD,\n" +
            "  driver.TRUCK_ID,\n" +
            "  truck.NUMBER,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME\n" +
            "FROM driver\n" +
            "  JOIN user ON driver.USER_ID = user.ID\n" +
            "  JOIN truck ON driver.TRUCK_ID = truck.ID\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID" +
            "  LIMIT ?,?";

    public static final String GET_DRIVERS_WITH_HEALTHY_TRUCKS = "SELECT\n" +
            "  driver.USER_ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD,\n" +
            "  driver.TRUCK_ID,\n" +
            "  truck.NUMBER,\n" +
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
            "  truck.NUMBER,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME\n" +
            "FROM driver\n" +
            "  JOIN user ON driver.USER_ID = user.ID\n" +
            "  JOIN truck ON driver.TRUCK_ID = truck.ID\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID\n" +
            "WHERE USER_ID = ?";

    public static final String GET_NUMBER_OF_DRIVERS = "SELECT COUNT(*) AS COUNT FROM driver";

    public static final String INSERT_DRIVER = "INSERT INTO driver VALUES (?, ?)";

    public static final String GET_ALL_REQUESTS_LIMITED = "SELECT\n" +
            "  request.ID,\n" +
            "  request.CARGO_WEIGHT,\n" +
            "  request.DEPARTURE_STATION_ID,\n" +
            "  departure_station.NAME      AS DEPARTURE_NAME,\n" +
            "  departure_station.ADDRESS   AS DEPARTURE_ADDRESS,\n" +
            "  request.DESTINATION_STATION_ID,\n" +
            "  destination_station.NAME    AS DESTINATION_NAME,\n" +
            "  destination_station.ADDRESS AS DESTINATION_ADDRESS\n" +
            "FROM request\n" +
            "  JOIN station departure_station ON request.DEPARTURE_STATION_ID = departure_station.ID\n" +
            "  JOIN station destination_station ON request.DESTINATION_STATION_ID = destination_station.ID" +
            "  LIMIT ?,?";

    public static final String GET_ALL_UNASSIGNED_REQUESTS = "SELECT\n" +
            "  request.ID,\n" +
            "  request.CARGO_WEIGHT,\n" +
            "  request.DEPARTURE_STATION_ID,\n" +
            "  departure_station.NAME      AS DEPARTURE_NAME,\n" +
            "  departure_station.ADDRESS   AS DEPARTURE_ADDRESS,\n" +
            "  request.DESTINATION_STATION_ID,\n" +
            "  destination_station.NAME    AS DESTINATION_NAME,\n" +
            "  destination_station.ADDRESS AS DESTINATION_ADDRESS\n" +
            "FROM request\n" +
            "  JOIN station departure_station ON request.DEPARTURE_STATION_ID = departure_station.ID\n" +
            "  JOIN station destination_station ON request.DESTINATION_STATION_ID = destination_station.ID\n" +
            "WHERE request.ID NOT IN (SELECT trip.REQUEST_ID\n" +
            "                         FROM trip)";

    public static final String GET_ALL_UNASSIGNED_REQUESTS_LIMITED = "SELECT\n" +
            "  request.ID,\n" +
            "  request.CARGO_WEIGHT,\n" +
            "  request.DEPARTURE_STATION_ID,\n" +
            "  departure_station.NAME      AS DEPARTURE_NAME,\n" +
            "  departure_station.ADDRESS   AS DEPARTURE_ADDRESS,\n" +
            "  request.DESTINATION_STATION_ID,\n" +
            "  destination_station.NAME    AS DESTINATION_NAME,\n" +
            "  destination_station.ADDRESS AS DESTINATION_ADDRESS\n" +
            "FROM request\n" +
            "  JOIN station departure_station ON request.DEPARTURE_STATION_ID = departure_station.ID\n" +
            "  JOIN station destination_station ON request.DESTINATION_STATION_ID = destination_station.ID\n" +
            "WHERE request.ID NOT IN (SELECT trip.REQUEST_ID\n" +
            "                         FROM trip)" +
            "  LIMIT ?,?";

    public static final String GET_NUMBER_OF_REQUESTS = "SELECT COUNT(*) AS COUNT FROM request";

    public static final String GET_NUMBER_OF_UNASSIGNED_REQUESTS = "SELECT COUNT(*) AS COUNT FROM request " +
            "WHERE request.ID NOT IN (SELECT trip.REQUEST_ID\n" +
            "                         FROM trip)";

    public static final String GET_DRIVER_BY_REQUEST = "SELECT\n" +
            "  driver.USER_ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD,\n" +
            "  driver.TRUCK_ID,\n" +
            "  truck.NUMBER,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME\n" +
            "FROM driver\n" +
            "  JOIN user ON driver.USER_ID = user.ID\n" +
            "  JOIN truck ON driver.TRUCK_ID = truck.ID\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID\n" +
            "WHERE driver.USER_ID = (SELECT trip.DRIVER_ID\n" +
            "                        FROM trip\n" +
            "                        WHERE trip.REQUEST_ID = ?)";

    public static final String INSERT_REQUEST = "INSERT INTO request " +
            "(DEPARTURE_STATION_ID, DESTINATION_STATION_ID, CARGO_WEIGHT) VALUES (?, ?, ?)";

    public static final String DELETE_REQUEST = "DELETE FROM request WHERE request.ID = ?";

    public static final String GET_ALL_TRIPS_LIMITED = "SELECT\n" +
            "  trip.ID,\n" +
            "  trip.REQUEST_ID,\n" +
            "  request.CARGO_WEIGHT,\n" +
            "  request.DEPARTURE_STATION_ID,\n" +
            "  departure_station.NAME      AS DEPARTURE_NAME,\n" +
            "  departure_station.ADDRESS   AS DEPARTURE_ADDRESS,\n" +
            "  request.DESTINATION_STATION_ID,\n" +
            "  destination_station.NAME    AS DESTINATION_NAME,\n" +
            "  destination_station.ADDRESS AS DESTINATION_ADDRESS,\n" +
            "  trip.DRIVER_ID AS USER_ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD,\n" +
            "  driver.TRUCK_ID,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME,\n" +
            "  trip.IS_COMPLETE\n" +
            "FROM trip\n" +
            "  JOIN request ON trip.REQUEST_ID = request.ID\n" +
            "  JOIN station departure_station ON request.DEPARTURE_STATION_ID = departure_station.ID\n" +
            "  JOIN station destination_station ON request.DESTINATION_STATION_ID = destination_station.ID\n" +
            "  JOIN driver ON trip.DRIVER_ID = driver.USER_ID\n" +
            "  JOIN user ON driver.USER_ID = user.ID\n" +
            "  JOIN truck ON driver.TRUCK_ID = truck.ID\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID" +
            "  LIMIT ?,?";

    public static final String GET_TRIPS_BY_DRIVER_LIMITED = "SELECT\n" +
            "  trip.ID,\n" +
            "  trip.REQUEST_ID,\n" +
            "  request.CARGO_WEIGHT,\n" +
            "  request.DEPARTURE_STATION_ID,\n" +
            "  departure_station.NAME      AS DEPARTURE_NAME,\n" +
            "  departure_station.ADDRESS   AS DEPARTURE_ADDRESS,\n" +
            "  request.DESTINATION_STATION_ID,\n" +
            "  destination_station.NAME    AS DESTINATION_NAME,\n" +
            "  destination_station.ADDRESS AS DESTINATION_ADDRESS,\n" +
            "  trip.DRIVER_ID AS USER_ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD,\n" +
            "  driver.TRUCK_ID,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME,\n" +
            "  trip.IS_COMPLETE\n" +
            "FROM trip\n" +
            "  JOIN request ON trip.REQUEST_ID = request.ID\n" +
            "  JOIN station departure_station ON request.DEPARTURE_STATION_ID = departure_station.ID\n" +
            "  JOIN station destination_station ON request.DESTINATION_STATION_ID = destination_station.ID\n" +
            "  JOIN driver ON trip.DRIVER_ID = driver.USER_ID\n" +
            "  JOIN user ON driver.USER_ID = user.ID\n" +
            "  JOIN truck ON driver.TRUCK_ID = truck.ID\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID\n" +
            "WHERE trip.DRIVER_ID = ?" +
            "  LIMIT ?,?";

    public static final String GET_NUMBER_OF_TRIPS = "SELECT COUNT(*) AS COUNT FROM trip";

    public static final String GET_NUMBER_OF_TRIPS_BY_DRIVER = "SELECT COUNT(*) AS COUNT FROM trip " +
            "WHERE trip.DRIVER_ID = ?";

    public static final String GET_NUMBER_OF_COMPLETED_TRIPS_BY_DRIVER = "SELECT COUNT(*) AS COUNT FROM trip " +
            "WHERE trip.DRIVER_ID = ? " +
            "AND trip.IS_COMPLETE = 1";

    public static final String GET_REQUEST_BY_ID = "SELECT\n" +
            "  request.ID,\n" +
            "  request.CARGO_WEIGHT,\n" +
            "  request.DEPARTURE_STATION_ID,\n" +
            "  departure_station.NAME      AS DEPARTURE_NAME,\n" +
            "  departure_station.ADDRESS   AS DEPARTURE_ADDRESS,\n" +
            "  request.DESTINATION_STATION_ID,\n" +
            "  destination_station.NAME    AS DESTINATION_NAME,\n" +
            "  destination_station.ADDRESS AS DESTINATION_ADDRESS\n" +
            "FROM request\n" +
            "  JOIN station departure_station ON request.DEPARTURE_STATION_ID = departure_station.ID\n" +
            "  JOIN station destination_station ON request.DESTINATION_STATION_ID = destination_station.ID\n" +
            "WHERE request.ID = ?";

    public static final String GET_TRUCK_BY_DRIVER_ID = "SELECT\n" +
            "  truck.ID,\n" +
            "  truck.NUMBER,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME\n" +
            "FROM driver\n" +
            "  JOIN truck ON driver.TRUCK_ID = truck.ID\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID\n" +
            "WHERE driver.USER_ID = ?";

    public static final String INSERT_TRIP = "INSERT INTO trip " +
            "(REQUEST_ID, DRIVER_ID, IS_COMPLETE) VALUES (?, ?, FALSE)";

    public static final String GET_TRIP_BY_ID = "SELECT\n" +
            "  trip.ID,\n" +
            "  trip.REQUEST_ID,\n" +
            "  request.CARGO_WEIGHT,\n" +
            "  request.DEPARTURE_STATION_ID,\n" +
            "  departure_station.NAME      AS DEPARTURE_NAME,\n" +
            "  departure_station.ADDRESS   AS DEPARTURE_ADDRESS,\n" +
            "  request.DESTINATION_STATION_ID,\n" +
            "  destination_station.NAME    AS DESTINATION_NAME,\n" +
            "  destination_station.ADDRESS AS DESTINATION_ADDRESS,\n" +
            "  trip.DRIVER_ID AS USER_ID,\n" +
            "  user.LOGIN,\n" +
            "  user.PASSWORD,\n" +
            "  driver.TRUCK_ID,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME,\n" +
            "  trip.IS_COMPLETE\n" +
            "FROM trip\n" +
            "  JOIN request ON trip.REQUEST_ID = request.ID\n" +
            "  JOIN station departure_station ON request.DEPARTURE_STATION_ID = departure_station.ID\n" +
            "  JOIN station destination_station ON request.DESTINATION_STATION_ID = destination_station.ID\n" +
            "  JOIN driver ON trip.DRIVER_ID = driver.USER_ID\n" +
            "  JOIN user ON driver.USER_ID = user.ID\n" +
            "  JOIN truck ON driver.TRUCK_ID = truck.ID\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID\n" +
            "WHERE trip.ID = ?";

    public static final String CHANGE_TRIP_STATE = "UPDATE trip\n" +
            "SET trip.IS_COMPLETE = ?\n" +
            "WHERE trip.ID = ?";

    public static final String GET_ALL_TRUCKS_LIMITED = "SELECT\n" +
            "  truck.ID,\n" +
            "  truck.NUMBER,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME\n" +
            "FROM truck\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID" +
            "  LIMIT ?,?";

    public static final String GET_TRUCK_BY_ID = "SELECT\n" +
            "  truck.ID,\n" +
            "  truck.NUMBER,\n" +
            "  truck.CAPACITY,\n" +
            "  truck.STATE_ID,\n" +
            "  truck_state.STATE_NAME\n" +
            "FROM truck\n" +
            "  JOIN truck_state ON truck.STATE_ID = truck_state.ID\n" +
            "WHERE truck.ID = ?";

    public static final String GET_NUMBER_OF_TRUCKS = "SELECT COUNT(*) AS COUNT FROM truck";

    public static final String CHANGE_TRUCK_STATE = "UPDATE truck\n" +
            "SET truck.STATE_ID = ?\n" +
            "WHERE truck.ID = ?";

    public static final String INSERT_TRUCK = "INSERT INTO truck (NUMBER, CAPACITY, STATE_ID) VALUES (?, ?, 1)";

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
            "WHERE user.LOGIN = ? AND user.PASSWORD = MD5(?)";

    public static final String INSERT_USER = "INSERT INTO user (LOGIN, PASSWORD) VALUES (?, MD5(?))";

    public static final String GET_ALL_STATIONS = "SELECT\n" +
            "  station.ID,\n" +
            "  station.NAME,\n" +
            "  station.ADDRESS\n" +
            "FROM station";

    private DatabaseQuery() {
    }
}
