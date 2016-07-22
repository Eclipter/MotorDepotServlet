package by.bsu.dektiarev.dao.impl.jdbc;

import by.bsu.dektiarev.dao.StationDAO;
import by.bsu.dektiarev.dao.util.ColumnName;
import by.bsu.dektiarev.dao.util.DatabaseQuery;
import by.bsu.dektiarev.dao.util.pool.ConnectionPool;
import by.bsu.dektiarev.entity.Station;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.DatabaseConnectionException;
import by.bsu.dektiarev.exception.ExceptionalMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 22.07.2016.
 */
public class StationDAOJDBCImpl implements StationDAO {

    @Override
    public List<Station> getAllStations() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_ALL_STATIONS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getStationsFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    private List<Station> getStationsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Station> stationList = new ArrayList<>();
        while(resultSet.next()) {
            Integer id = resultSet.getInt(ColumnName.ID);
            String name = resultSet.getString(ColumnName.NAME);
            String address = resultSet.getString(ColumnName.ADDRESS);
            Station station = new Station();
            station.setId(id);
            station.setName(name);
            station.setAddress(address);
            stationList.add(station);
        }
        return stationList;
    }
}
