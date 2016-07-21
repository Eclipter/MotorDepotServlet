package by.bsu.dektiarev.action;

import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.util.PageNameConstant;
import by.bsu.dektiarev.util.PagesBundleManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action, responsible for providing authentication form
 * Created by USER on 09.06.2016.
 */
public class GetLoginFormAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        return PagesBundleManager.getProperty(PageNameConstant.LOGIN);
    }
}
