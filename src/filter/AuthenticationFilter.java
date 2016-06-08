package filter;

import action.util.ActionEnum;
import bean.UserInfoBean;
import util.ConfigurationManager;
import util.PageNamesConstants;
import util.RequestParametersNames;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by USER on 15.05.2016.
 */
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String contextPath = req.getContextPath();
        HttpSession session = req.getSession(false);
        String command = req.getParameter(RequestParametersNames.COMMAND);
        ActionEnum actionEnum = ActionEnum.valueOf(command.toUpperCase());
        UserInfoBean userInfoBean = (UserInfoBean) session.getAttribute(RequestParametersNames.USER);
        if(session == null || userInfoBean == null) {
            if(!ActionEnum.SIGNUP_FORM.equals(actionEnum) && !ActionEnum.SIGNUP.equals(actionEnum)
                    && !ActionEnum.LOGIN.equals(actionEnum)) {
                res.sendRedirect(contextPath + ConfigurationManager.getProperty(PageNamesConstants.LOGIN));
            }
            else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
        else if(!userInfoBean.isAdmin() &&
                (ActionEnum.GET_SETTING_FORM.equals(actionEnum) || ActionEnum.GET_TRUCKS.equals(actionEnum))) {
            res.sendRedirect(contextPath + ConfigurationManager.getProperty(PageNamesConstants.LOGIN));
        }
        else {
            filterChain.doFilter(servletRequest, servletResponse);
            //TODO
        }
    }

    @Override
    public void destroy() {

    }
}
