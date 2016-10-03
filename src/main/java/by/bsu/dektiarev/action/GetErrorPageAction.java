package by.bsu.dektiarev.action;

import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.util.PageNameConstant;
import by.bsu.dektiarev.util.PagesBundleManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action that is used only by filters, in order to redirect user to error page.
 * Created by USER on 03.10.2016.
 */
public class GetErrorPageAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        return PagesBundleManager.getProperty(PageNameConstant.ERROR);
    }
}
