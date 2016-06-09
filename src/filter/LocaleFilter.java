package filter;

import exception.ExceptionalMessage;
import util.ConfigurationManager;
import util.PageNamesConstants;
import util.RequestParametersNames;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by USER on 09.06.2016.
 */
public class LocaleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String contextPath = req.getContextPath();
        String languageParameter = req.getParameter(RequestParametersNames.LANGUAGE);

        if(languageParameter != null) {
            if(!languageParameter.equals("ru") && !languageParameter.equals("en")) {
                req.getSession().setAttribute(RequestParametersNames.ERROR_MESSAGE, ExceptionalMessage.WRONG_COMMAND);
                res.sendRedirect(contextPath + ConfigurationManager.getProperty(PageNamesConstants.ERROR));
            }
            else {
                req.getSession().setAttribute(RequestParametersNames.LANGUAGE, languageParameter);
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
        else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
