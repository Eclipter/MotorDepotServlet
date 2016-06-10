package action;

import exception.ActionExecutionException;
import util.PageNameConstant;
import util.PagesBundleManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by USER on 09.06.2016.
 */
public class GetLoginFormAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        return PagesBundleManager.getProperty(PageNameConstant.LOGIN);
    }
}
