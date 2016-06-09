package action;

import action.bean.ActionResponse;
import action.bean.ActionType;
import exception.ActionExecutionException;
import util.ConfigurationManager;
import util.PageNamesConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by USER on 09.06.2016.
 */
public class GetLoginFormAction implements Action {

    @Override
    public ActionResponse execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        return new ActionResponse(ConfigurationManager.getProperty(PageNamesConstants.LOGIN),
                ActionType.FORWARD);
    }
}
