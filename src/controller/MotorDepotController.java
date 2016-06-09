package controller;

import action.Action;
import action.bean.ActionResponse;
import action.bean.ActionType;
import action.util.CommandHelper;
import exception.ActionExecutionException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;
import util.PageNamesConstants;
import util.RequestParametersNames;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by USER on 20.04.2016.
 */
public class MotorDepotController extends HttpServlet {

    private static final long serialVersionUID = -811960845105124825L;
    private static final Logger logger = LogManager.getLogger();

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String command = req.getParameter(RequestParametersNames.COMMAND);
            logger.info("processing " + command + " command");
            Action action = CommandHelper.getCommand(command);
            if(action == null) {
                throw new ActionExecutionException(ExceptionalMessage.NO_ACTION);
            }
            ActionResponse result = action.execute(req, resp);
            if(result.getActionType() == ActionType.FORWARD) {
                req.getRequestDispatcher(result.getUrl()).forward(req, resp);
            }
            else {
                resp.sendRedirect(result.getUrl());
            }
        } catch (ActionExecutionException e) {
            logger.error(e);
            String message = e.getMessage();
            if(e.getCause() != null) {
                message += ": " + e.getCause().getMessage();
            }
            req.setAttribute(RequestParametersNames.ERROR_MESSAGE, message);
            req.getRequestDispatcher(ConfigurationManager.getProperty(PageNamesConstants.ERROR)).forward(req, resp);
        } catch (Exception e) {
            logger.error("unexpected error", e);
            req.setAttribute(RequestParametersNames.ERROR_MESSAGE, "Unexpected error: " + e.getMessage());
            req.getRequestDispatcher(ConfigurationManager.getProperty(PageNamesConstants.ERROR)).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("POST request processing");
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("GET request processing");
        processRequest(req, resp);
    }
}
