package by.bsu.dektiarev.dao;

import by.bsu.dektiarev.entity.Station;
import by.bsu.dektiarev.exception.DAOException;

import java.util.List;

/**
 * DAO interface used to operate on STATION table
 * Created by USER on 22.07.2016.
 */
public interface StationDAO extends GenericDAO {

    /**
     * Retrieves all stations
     *
     * @return list of stations
     * @throws DAOException
     */
    List<Station> getAllStations() throws DAOException;
}
