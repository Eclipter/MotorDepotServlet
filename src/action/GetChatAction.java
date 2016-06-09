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
 * Created by USER on 07.06.2016.
 */
public class GetChatAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public ActionResponse execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("redirecting to chat page");
        return new ActionResponse(ConfigurationManager.getProperty(PageNamesConstants.CHAT),
                ActionType.FORWARD);
    }
}
