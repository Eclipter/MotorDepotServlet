package by.bsu.dektiarev.dao.impl.jpa;

import by.bsu.dektiarev.dao.StationDAO;
import by.bsu.dektiarev.entity.Station;
import by.bsu.dektiarev.exception.DAOException;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by USER on 22.07.2016.
 */
public class StationDAOJPAImpl extends GenericDAOJPAImpl implements StationDAO {

    private static final String GET_ALL_QUERY = "Station.getAll";

    @Override
    public List<Station> getAllStations() throws DAOException {
        TypedQuery<Station> query = getManager().createNamedQuery(GET_ALL_QUERY, Station.class);
        return query.getResultList();
    }
}