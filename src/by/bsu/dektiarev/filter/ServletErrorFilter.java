package by.bsu.dektiarev.filter;

import by.bsu.dektiarev.util.PageNameConstant;
import by.bsu.dektiarev.util.PagesBundleManager;
import by.bsu.dektiarev.util.RequestParameterName;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by USER on 16.06.2016.
 */
public class ServletErrorFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String contextPath = req.getContextPath();

        String errorMessage = (String) req.getSession().getServletContext().
                getAttribute(RequestParameterName.ERROR_MESSAGE);
        if(errorMessage != null) {
            req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE, errorMessage);
            res.sendRedirect(contextPath + PagesBundleManager.getProperty(PageNameConstant.ERROR));
        }
        else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
