package action;

import exception.ActionExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;
import util.PageNamesConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by USER on 08.06.2016.
 */
public class GetIndexPageAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        logger.info("redirecting to index page");
        return ConfigurationManager.getProperty(PageNamesConstants.INDEX);
    }
}
