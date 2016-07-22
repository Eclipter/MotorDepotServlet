package by.bsu.dektiarev.dao;

import by.bsu.dektiarev.entity.Station;
import by.bsu.dektiarev.exception.DAOException;

import java.util.List;

/**
 * Created by USER on 22.07.2016.
 */
public interface StationDAO extends GenericDAO {

    List<Station> getAllStations() throws DAOException;
}
