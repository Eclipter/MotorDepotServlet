package by.bsu.dektiarev.action;

import by.bsu.dektiarev.exception.ActionExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.bsu.dektiarev.util.PageNameConstant;
import by.bsu.dektiarev.util.PagesBundleManager;
import by.bsu.dektiarev.util.RequestParameterName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action, responsible for logout process
 * Created by USER on 25.04.2016.
 */
public class LogoutAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        LOG.info("invalidating session");
        req.getSession().removeAttribute(RequestParameterName.USER);
        return PagesBundleManager.getProperty(PageNameConstant.LOGIN);
    }
}
