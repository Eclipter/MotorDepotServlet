package filter;

import action.util.ActionEnum;
import exception.ExceptionalMessage;
import util.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter used to check if the client specified any command to execute
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

        String command = req.getParameter(RequestParameterName.COMMAND);
        if(command == null) {
            req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                    InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                            ExceptionalMessage.NO_COMMAND,
                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
            res.sendRedirect(contextPath + PagesBundleManager.getProperty(PageNameConstant.ERROR));
            return;
        }

        try {
            ActionEnum.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                    InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                            ExceptionalMessage.WRONG_COMMAND,
                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
            res.sendRedirect(contextPath + PagesBundleManager.getProperty(PageNameConstant.ERROR));
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
