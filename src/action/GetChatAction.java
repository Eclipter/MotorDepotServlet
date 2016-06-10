package action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.PageNameConstant;
import util.PagesBundleManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Action, responsible for providing chat page
 * Created by USER on 07.06.2016.
 */
public class GetChatAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("redirecting to chat page");
        return PagesBundleManager.getProperty(PageNameConstant.CHAT);
    }
}
