package by.bsu.dektiarev.controller;

import by.bsu.dektiarev.action.Action;
import by.bsu.dektiarev.action.util.CommandHelper;
import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.exception.CommandNotFoundException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.bsu.dektiarev.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main controller that handles GET and POST requests from clients
 * Created by USER on 20.04.2016.
 */
public class MotorDepotController extends HttpServlet {

    private static final long serialVersionUID = -811960845105124825L;
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Executes action depending on command that is kept in request parameters
     *
     * @param req  servlet request
     * @param resp servlet response
     * @return URL, to which client must be forwarded or redirected
     * @throws ActionExecutionException
     */
    private String executeAction(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        String command = req.getParameter(RequestParameterName.COMMAND);
        LOG.info("processing " + command + " command");
        if(command == null) {
            return PagesBundleManager.getProperty(PageNameConstant.INDEX);
        }
        else {
            Action action = CommandHelper.getCommand(command);
            if (action == null) {
                throw new CommandNotFoundException(ExceptionalMessageKey.NO_COMMAND);
            }
            return action.execute(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("POST request processing");
        try {
            String result = executeAction(req, resp);
            resp.sendRedirect(result);
        } catch (ActionExecutionException e) {
            LOG.error(e);
            String message = InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                    e.getMessage(),
                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE));
            req.setAttribute(RequestParameterName.ERROR_MESSAGE, message);
            req.getRequestDispatcher(PagesBundleManager.getProperty(PageNameConstant.ERROR)).forward(req, resp);
        } catch (Exception e) {
            LOG.error("UNEXPECTED ERROR", e);
            req.setAttribute(RequestParameterName.ERROR_MESSAGE,
                    InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                            ExceptionalMessageKey.UNEXPECTED,
                            (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)) + " " + e.getMessage());
            req.getRequestDispatcher(PagesBundleManager.getProperty(PageNameConstant.ERROR)).forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("GET request processing");
        try {
            String result = executeAction(req, resp);
            req.getRequestDispatcher(result).forward(req, resp);
        } catch (ActionExecutionException e) {
            LOG.error(e);
            String message = InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                    e.getMessage(),
                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE));
            req.setAttribute(RequestParameterName.ERROR_MESSAGE, message);
            req.getRequestDispatcher(PagesBundleManager.getProperty(PageNameConstant.ERROR)).forward(req, resp);
        } catch (Exception e) {
            LOG.error("UNEXPECTED ERROR", e);
            req.setAttribute(RequestParameterName.ERROR_MESSAGE,
                    InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                            ExceptionalMessageKey.UNEXPECTED,
                            (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)) + " " + e.getMessage());
            req.getRequestDispatcher(PagesBundleManager.getProperty(PageNameConstant.ERROR)).forward(req, resp);
        }
    }
}
