package by.bsu.dektiarev.dao.impl.jpa;

import by.bsu.dektiarev.dao.StationDAO;
import by.bsu.dektiarev.entity.Station;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by USER on 22.07.2016.
 */
public class StationDAOJPAImpl extends GenericDAOJPAImpl implements StationDAO {

    private static final String GET_ALL_QUERY = "Station.getAll";

    public StationDAOJPAImpl(EntityManager manager) {
        super(manager);
    }

    @Override
    public List<Station> getAllStations() throws DAOException {
        try {
            TypedQuery<Station> query = getManager().createNamedQuery(GET_ALL_QUERY, Station.class);
            return query.getResultList();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }
}