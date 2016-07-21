package by.bsu.dektiarev.action;

import by.bsu.dektiarev.exception.ActionExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface for all actions
 * Created by USER on 25.04.2016.
 */
public interface Action {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException;
}
