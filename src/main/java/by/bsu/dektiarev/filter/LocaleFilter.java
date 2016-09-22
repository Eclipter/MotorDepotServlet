package by.bsu.dektiarev.filter;

import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import by.bsu.dektiarev.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String contextPath = req.getContextPath();
        String languageParameter = req.getParameter(RequestParameterName.LANGUAGE);

        if(languageParameter != null) {
            if(!languageParameter.equals("ru") && !languageParameter.equals("en")) {
                LOG.warn("unknown language specified");
                req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                        InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                                ExceptionalMessageKey.WRONG_COMMAND,
                        (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
                res.sendRedirect(contextPath + PagesBundleManager.getProperty(PageNameConstant.ERROR));
            }
            else {
                LOG.info("new language setup: {}", languageParameter);
                LOG.info("locale filter passed");
                req.getSession().setAttribute(RequestParameterName.LANGUAGE, languageParameter);
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
        else {
            LOG.info("locale filter passed");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
