package filter;

import action.util.ActionEnum;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter that checks if the signup form fields are filled correctly
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

        String command = req.getParameter(RequestParameterName.COMMAND);
        ActionEnum actionEnum = ActionEnum.valueOf(command.toUpperCase());
        if(!ActionEnum.SIGNUP.equals(actionEnum)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            String password = req.getParameter(RequestParameterName.PASSWORD);
            String passwordRepeat = req.getParameter(RequestParameterName.PASSWORD_REPEAT);
            String truckCapacity = req.getParameter(RequestParameterName.TRUCK_CAPACITY);

            if(password == null || passwordRepeat == null || truckCapacity == null) {
                logger.warn("missing signup parameters");
                req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                        InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                                ExceptionalMessage.MISSING_REQUEST_PARAMETERS,
                                (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
                res.sendRedirect(contextPath + PagesBundleManager.getProperty(PageNameConstant.ERROR));
            }
            else if(!password.equals(passwordRepeat)) {
                logger.warn(ExceptionalMessage.PASSWORDS_NOT_EQUAL);
                req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                        InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                                ExceptionalMessage.PASSWORDS_NOT_EQUAL,
                        (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
                res.sendRedirect(contextPath + PagesBundleManager.getProperty(PageNameConstant.SIGNUP_FORM));
            }
            else {
                try {
                    int capacity = Integer.parseInt(truckCapacity);
                    if(capacity <= 0) {
                        logger.warn("truck capacity below zero");
                        req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                                InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                                        ExceptionalMessage.TRUCK_CAPACITY_BELOW_ZERO,
                                        (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
                        res.sendRedirect(contextPath + PagesBundleManager.getProperty(PageNameConstant.SIGNUP_FORM));
                        return;
                    }
                }
                catch (NumberFormatException e) {
                    logger.warn("wrong input for truck capacity");
                    req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                            InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                                    ExceptionalMessage.WRONG_INPUT_FOR_CAPACITY,
                                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
                    res.sendRedirect(contextPath + PagesBundleManager.getProperty(PageNameConstant.SIGNUP_FORM));
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
