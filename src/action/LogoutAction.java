package action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.PageNameConstant;
import util.PagesBundleManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Logout process
 * Created by USER on 25.04.2016.
 */
public class LogoutAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("invalidating session");
        req.getSession().invalidate();
        return PagesBundleManager.getProperty(PageNameConstant.LOGIN);
    }
}
