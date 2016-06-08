package filter;

import action.util.ActionEnum;
import exception.ExceptionalMessage;
import util.ConfigurationManager;
import util.PageNamesConstants;
import util.RequestParametersNames;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by USER on 08.06.2016.
 */
public class CommandFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String contextPath = req.getContextPath();

        String command = req.getParameter(RequestParametersNames.COMMAND);
        if(command == null) {
            req.getSession().setAttribute(RequestParametersNames.ERROR_MESSAGE, ExceptionalMessage.NO_COMMAND);
            res.sendRedirect(contextPath + ConfigurationManager.getProperty(PageNamesConstants.ERROR));
            return;
        }
        ActionEnum actionEnum;
        try {
            actionEnum = ActionEnum.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute(RequestParametersNames.ERROR_MESSAGE, ExceptionalMessage.WRONG_COMMAND);
            res.sendRedirect(contextPath + ConfigurationManager.getProperty(PageNamesConstants.ERROR));
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
