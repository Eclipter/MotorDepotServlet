package servlet;

import action.Action;
import action.util.CommandHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        String command = req.getParameter(RequestParametersNames.COMMAND);
        logger.info("processing " + command + " command");
        Action action = CommandHelper.getCommand(command);
        String result = action.execute(req, resp);
        req.getRequestDispatcher(result).forward(req, resp);
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
