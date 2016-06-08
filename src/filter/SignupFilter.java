package filter;

import exception.DAOException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;

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

        if(!"signup".equals(req.getParameter("command"))) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String password = req.getParameter("password");
        String passwordRepeat = req.getParameter("passwordRepeat");
        String truckCapacity = req.getParameter("truckCapacity");

        if(!password.equals(passwordRepeat)) {
            logger.warn(ExceptionalMessage.PASSWORDS_NOT_EQUAL);
            req.getSession().setAttribute("errorMessage", ExceptionalMessage.PASSWORDS_NOT_EQUAL);
            res.sendRedirect(contextPath + ConfigurationManager.getProperty("signup_form"));
            return;
        }

        try {
            int capacity = Integer.parseInt(truckCapacity);
            if(capacity <= 0) {
                throw new DAOException(ExceptionalMessage.TRUCK_CAPACITY_BELOW_ZERO);
            }
        } catch (DAOException e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            res.sendRedirect(contextPath + ConfigurationManager.getProperty("signup_form"));
            return;
        }
        catch (NumberFormatException e) {
            req.getSession().setAttribute("errorMessage", ExceptionalMessage.WRONG_INPUT_FOR_CAPACITY);
            res.sendRedirect(contextPath + ConfigurationManager.getProperty("signup_form"));
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
