package by.bsu.dektiarev.filter;

import by.bsu.dektiarev.action.util.ActionEnum;
import by.bsu.dektiarev.exception.ExceptionalMessage;
import by.bsu.dektiarev.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter used to check if the client specified any command to execute
 * Created by USER on 08.06.2016.
 */
public class CommandFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger();

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
            LOG.warn("no command to execute");
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
            LOG.warn("wrong command name");
            req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                    InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                            ExceptionalMessage.WRONG_COMMAND,
                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
            res.sendRedirect(contextPath + PagesBundleManager.getProperty(PageNameConstant.ERROR));
            return;
        }
        LOG.info("command filter passed");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
