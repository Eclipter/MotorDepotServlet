package by.bsu.dektiarev.action;

import by.bsu.dektiarev.exception.ActionExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.bsu.dektiarev.util.PageNameConstant;
import by.bsu.dektiarev.util.PagesBundleManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action, responsible for providing signup form
 * Created by USER on 15.05.2016.
 */
public class GetSignupFormAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        LOG.info("requesting signup form");
        return PagesBundleManager.getProperty(PageNameConstant.SIGNUP_FORM);
    }
}
