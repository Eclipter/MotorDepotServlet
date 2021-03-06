package by.bsu.dektiarev.filter;

import by.bsu.dektiarev.action.util.ActionEnum;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import by.bsu.dektiarev.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Filter that checks if the signup form fields are filled correctly
 * Created by USER on 15.05.2016.
 */
public class SignupFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String command = req.getParameter(RequestParameterName.COMMAND);
        if(command != null) {
            try {
                ActionEnum.valueOf(command.toUpperCase());
            } catch (IllegalArgumentException ex) {
                req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                        InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                                ExceptionalMessageKey.WRONG_COMMAND,
                                (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
                res.sendRedirect(URLConstant.GET_ERROR_PAGE);
                return;
            }
        }
        if (command == null || !ActionEnum.SIGNUP.equals(ActionEnum.valueOf(command.toUpperCase()))) {
            LOG.info("signup filter passed");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String username = req.getParameter(RequestParameterName.USERNAME);
            String password = req.getParameter(RequestParameterName.PASSWORD);
            String passwordRepeat = req.getParameter(RequestParameterName.PASSWORD_REPEAT);
            String truckNumber = req.getParameter(RequestParameterName.TRUCK_NUMBER);
            String truckCapacity = req.getParameter(RequestParameterName.TRUCK_CAPACITY);

            if (username == null || password == null || passwordRepeat == null || truckNumber == null ||
                    truckCapacity == null || username.equals("") || password.equals("") ||
                    passwordRepeat.equals("") || truckNumber.equals("") ||
                    truckCapacity.equals("")) {
                LOG.warn("missing signup parameters");
                req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                        InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                                ExceptionalMessageKey.MISSING_REQUEST_PARAMETERS,
                                (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
                res.sendRedirect(URLConstant.GET_SIGNUP_FORM);
            } else if (!password.equals(passwordRepeat)) {
                LOG.warn(ExceptionalMessageKey.PASSWORDS_NOT_EQUAL);
                req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                        InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                                ExceptionalMessageKey.PASSWORDS_NOT_EQUAL,
                                (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
                res.sendRedirect(URLConstant.GET_SIGNUP_FORM);
            } else {
                Pattern usernamePattern = Pattern.compile(CredentialPattern.USERNAME_PATTERN);
                Matcher usernameMatcher = usernamePattern.matcher(username);
                Pattern passwordPattern = Pattern.compile(CredentialPattern.PASSWORD_PATTERN);
                Matcher passwordMatcher = passwordPattern.matcher(password);
                if(!usernameMatcher.matches() || !passwordMatcher.matches()) {
                    req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                            InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                                    ExceptionalMessageKey.WRONG_INPUT_PARAMETERS,
                                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
                    res.sendRedirect(URLConstant.GET_SIGNUP_FORM);
                    return;
                }
                try {
                    double capacity = Double.parseDouble(truckCapacity);
                    if (capacity <= 0) {
                        LOG.warn("truck capacity below zero");
                        req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                                InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                                        ExceptionalMessageKey.TRUCK_CAPACITY_BELOW_ZERO,
                                        (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
                        res.sendRedirect(URLConstant.GET_SIGNUP_FORM);
                        return;
                    }
                } catch (NumberFormatException e) {
                    LOG.warn("wrong input for truck capacity");
                    req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                            InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                                    ExceptionalMessageKey.WRONG_INPUT_FOR_CAPACITY,
                                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
                    res.sendRedirect(URLConstant.GET_SIGNUP_FORM);
                    return;
                }
                LOG.info("signup filter passed");
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
