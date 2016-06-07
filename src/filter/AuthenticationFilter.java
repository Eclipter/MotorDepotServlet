package filter;

import action.controller.UserController;
import util.ConfigurationManager;

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
        String command = req.getParameter("command");
        if(session == null || session.getAttribute("user") == null) {
            if(!"signup_form".equals(command) && !"signup".equals(command) && !"login".equals(command)) {
                res.sendRedirect(contextPath + ConfigurationManager.getProperty("login"));
            }
            else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
        else {
            UserController userController = (UserController) session.getAttribute("user");
            if(!userController.isAdmin() &&
                    ("set_form".equals(command) || "trucks".equals(command))) {
                    res.sendRedirect(contextPath + ConfigurationManager.getProperty("login"));
            }
            else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
