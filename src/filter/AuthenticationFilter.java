package filter;

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
        if(session == null || session.getAttribute(RequestParametersNames.USER) == null) {
            if(!"signup_form".equals(command) && !"signup".equals(command) && !"login".equals(command)) {
                res.sendRedirect(contextPath + ConfigurationManager.getProperty(PageNamesConstants.LOGIN));
                return;
            }
        }
        else {
            UserInfoBean userInfoBean = (UserInfoBean) session.getAttribute(RequestParametersNames.USER);
            if(!userInfoBean.isAdmin() &&
                    ("set_form".equals(command) || "trucks".equals(command))) {
                    res.sendRedirect(contextPath + ConfigurationManager.getProperty(PageNamesConstants.LOGIN));
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
