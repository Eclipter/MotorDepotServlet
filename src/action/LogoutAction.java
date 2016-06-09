package action;

import action.bean.ActionResponse;
import action.bean.ActionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;
import util.PageNamesConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Logout process
 * Created by USER on 25.04.2016.
 */
public class LogoutAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public ActionResponse execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("invalidating session");
        req.getSession().invalidate();
        return new ActionResponse(ConfigurationManager.getProperty(PageNamesConstants.LOGIN),
                ActionType.FORWARD);
    }
}
