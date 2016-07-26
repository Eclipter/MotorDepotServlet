package by.bsu.dektiarev.filter;

import by.bsu.dektiarev.action.util.ActionEnum;
import by.bsu.dektiarev.bean.UserInfoBean;
import by.bsu.dektiarev.util.PageNameConstant;
import by.bsu.dektiarev.util.PagesBundleManager;
import by.bsu.dektiarev.util.RequestParameterName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter used to check the page the client is requesting.
 * Created by USER on 15.05.2016.
 */
public class AuthenticationFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger();

    private static final ActionEnum[] ADMIN_COMMANDS = { ActionEnum.CHANGE_TRIP_STATE,
            ActionEnum.ADD_REQUEST, ActionEnum.GET_REQUESTS,
            ActionEnum.GET_TRIPS, ActionEnum.GET_ASSIGNATION_FORM,
            ActionEnum.ASSIGN_DRIVER_TO_A_TRIP, ActionEnum.DELETE_REQUEST
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String contextPath = req.getContextPath();
        HttpSession session = req.getSession(false);
        String command = req.getParameter(RequestParameterName.COMMAND);
        ActionEnum actionEnum = ActionEnum.valueOf(command.toUpperCase());
        UserInfoBean userInfoBean = (UserInfoBean) session.getAttribute(RequestParameterName.USER);
        if(userInfoBean == null) {
            if(!ActionEnum.GET_SIGNUP_FORM.equals(actionEnum) && !ActionEnum.SIGNUP.equals(actionEnum)
                    && !ActionEnum.LOGIN.equals(actionEnum) && !ActionEnum.GET_LOGIN_FORM.equals(actionEnum)) {
                LOG.warn("unauthorised access attempt");
                res.sendRedirect(contextPath + PagesBundleManager.getProperty(PageNameConstant.LOGIN));
                return;
            }
            else {
                LOG.info("authentication filter passed");
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        else if(!userInfoBean.isAdmin()) {
            for(ActionEnum forbiddenCommand : ADMIN_COMMANDS) {
                if(actionEnum == forbiddenCommand) {
                    LOG.warn("unauthorised access attempt");
                    res.sendRedirect(contextPath + PagesBundleManager.getProperty(PageNameConstant.LOGIN));
                    return;
                }
            }
        }
        LOG.info("authentication filter passed");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
