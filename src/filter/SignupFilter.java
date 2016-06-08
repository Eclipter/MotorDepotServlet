package filter;

import action.util.ActionEnum;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;
import util.PageNamesConstants;
import util.RequestParametersNames;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by USER on 15.05.2016.
 */
public class SignupFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String contextPath = req.getContextPath();

        String command = req.getParameter(RequestParametersNames.COMMAND);
        ActionEnum actionEnum = ActionEnum.valueOf(command.toUpperCase());
        if(!ActionEnum.SIGNUP.equals(actionEnum)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            String password = req.getParameter(RequestParametersNames.PASSWORD);
            String passwordRepeat = req.getParameter(RequestParametersNames.PASSWORD_REPEAT);
            String truckCapacity = req.getParameter(RequestParametersNames.TRUCK_CAPACITY);

            if(password == null || passwordRepeat == null || truckCapacity == null) {
                logger.warn("missing signup parameters");
                req.getSession().setAttribute(RequestParametersNames.ERROR_MESSAGE
                        , ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
                res.sendRedirect(contextPath + ConfigurationManager.getProperty(PageNamesConstants.ERROR));
            }
            else if(!password.equals(passwordRepeat)) {
                logger.warn(ExceptionalMessage.PASSWORDS_NOT_EQUAL);
                req.getSession().setAttribute(RequestParametersNames.ERROR_MESSAGE, ExceptionalMessage.PASSWORDS_NOT_EQUAL);
                res.sendRedirect(contextPath + ConfigurationManager.getProperty(PageNamesConstants.SIGNUP_FORM));
            }
            else {
                try {
                    int capacity = Integer.parseInt(truckCapacity);
                    if(capacity <= 0) {
                        logger.warn("truck capacity below zero");
                        req.getSession().setAttribute(RequestParametersNames.ERROR_MESSAGE
                                , ExceptionalMessage.TRUCK_CAPACITY_BELOW_ZERO);
                        res.sendRedirect(contextPath + ConfigurationManager.getProperty(PageNamesConstants.SIGNUP_FORM));
                        return;
                    }
                }
                catch (NumberFormatException e) {
                    logger.warn("wrong input for truck capacity");
                    req.getSession().setAttribute(RequestParametersNames.ERROR_MESSAGE,
                            ExceptionalMessage.WRONG_INPUT_FOR_CAPACITY);
                    res.sendRedirect(contextPath + ConfigurationManager.getProperty(PageNamesConstants.SIGNUP_FORM));
                    return;
                }

                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
