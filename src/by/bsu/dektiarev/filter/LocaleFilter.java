package by.bsu.dektiarev.filter;

import by.bsu.dektiarev.exception.ExceptionalMessage;
import by.bsu.dektiarev.util.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter that checks if the language parameter is present in the request.
 * And if yes, it is saved into the session attribute
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
        String languageParameter = req.getParameter(RequestParameterName.LANGUAGE);

        if(languageParameter != null) {
            if(!languageParameter.equals("ru") && !languageParameter.equals("en")) {
                req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                        InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                                ExceptionalMessage.WRONG_COMMAND,
                        (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
                res.sendRedirect(contextPath + PagesBundleManager.getProperty(PageNameConstant.ERROR));
            }
            else {
                req.getSession().setAttribute(RequestParameterName.LANGUAGE, languageParameter);
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
