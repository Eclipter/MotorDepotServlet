package by.bsu.dektiarev.action.test;

import by.bsu.dektiarev.action.Action;
import by.bsu.dektiarev.action.LoginAction;
import by.bsu.dektiarev.bean.UserInfoBean;
import by.bsu.dektiarev.dao.DriverDAO;
import by.bsu.dektiarev.dao.UserDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.entity.Driver;
import by.bsu.dektiarev.entity.User;
import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessage;
import by.bsu.dektiarev.util.RequestParameterName;
import by.bsu.dektiarev.util.URLConstant;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by USER on 21.06.2016.
 */
public class LoginActionTest {

    private static final String STUB_STRING = "STUB_STRING";

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private HttpSession session;

    @Mock
    private DAOFactory daoFactory;

    @Mock
    private UserDAO userDAO;

    @Mock
    private DriverDAO driverDAO;

    @InjectMocks
    Action loginAction = new LoginAction();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(req.getSession()).thenReturn(session);
        when(daoFactory.getDAOFromFactory(DAOType.USER)).thenReturn(userDAO);
        when(daoFactory.getDAOFromFactory(DAOType.DRIVER)).thenReturn(driverDAO);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void executeTestAdminWithCorrectLoginPass() throws Exception {
        String username = STUB_STRING;
        String password = STUB_STRING;
        when(req.getParameter(RequestParameterName.USERNAME)).thenReturn(username);
        when(req.getParameter(RequestParameterName.PASSWORD)).thenReturn(password);
        User confirmedUser = new User();
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setUser(confirmedUser);
        userInfoBean.setAdmin(true);

        when(userDAO.authenticateUser(username, password)).thenReturn(confirmedUser);
        when(driverDAO.searchByUser(confirmedUser)).thenReturn(null);
        String answer = loginAction.execute(req, resp);

        verify(driverDAO).searchByUser(confirmedUser);
        verify(session).setAttribute(RequestParameterName.USER, userInfoBean);
        assertEquals(answer, URLConstant.GET_INDEX_PAGE);
    }

    @Test
    public void executeTestDriverWithCorrectLoginPass() throws Exception {
        String username = STUB_STRING;
        String password = STUB_STRING;
        when(req.getParameter(RequestParameterName.USERNAME)).thenReturn(username);
        when(req.getParameter(RequestParameterName.PASSWORD)).thenReturn(password);
        User confirmedUser = new User();
        Driver confirmedDriver = new Driver();
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setUser(confirmedUser);
        userInfoBean.setAdmin(false);

        when(userDAO.authenticateUser(username, password)).thenReturn(confirmedUser);
        when(driverDAO.searchByUser(confirmedUser)).thenReturn(confirmedDriver);
        String answer = loginAction.execute(req, resp);

        verify(driverDAO).searchByUser(confirmedUser);
        verify(session).setAttribute(RequestParameterName.USER, userInfoBean);
        assertEquals(answer, URLConstant.GET_INDEX_PAGE);
    }

    @Test
    public void executeTestWithIncorrectLoginPass() throws Exception {
        String username = STUB_STRING;
        String password = STUB_STRING;
        when(req.getParameter(RequestParameterName.USERNAME)).thenReturn(username);
        when(req.getParameter(RequestParameterName.PASSWORD)).thenReturn(password);
        when(userDAO.authenticateUser(username, password)).thenReturn(null);

        expectedException.expect(ActionExecutionException.class);
        expectedException.expectMessage(ExceptionalMessage.WRONG_LOGIN_PASS);
        loginAction.execute(req, resp);
    }

    @Test
    public void executeTestWithAbsentLogin() throws Exception {
        String password = STUB_STRING;
        when(req.getParameter(RequestParameterName.PASSWORD)).thenReturn(password);

        expectedException.expect(ActionExecutionException.class);
        expectedException.expectMessage(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
        loginAction.execute(req, resp);
    }

    @Test
    public void executeTestWithAbsentPassword() throws Exception {
        String username = STUB_STRING;
        when(req.getParameter(RequestParameterName.USERNAME)).thenReturn(username);

        expectedException.expect(ActionExecutionException.class);
        expectedException.expectMessage(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
        loginAction.execute(req, resp);
    }

    @Test
    public void executeTestWithAbsentLoginAndPassword() throws Exception {

        expectedException.expect(ActionExecutionException.class);
        expectedException.expectMessage(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
        loginAction.execute(req, resp);
    }

    @Test
    public void executeTestWithDAOException() throws Exception {
        String exceptionalMessage = STUB_STRING;
        String username = STUB_STRING;
        String password = STUB_STRING;
        when(req.getParameter(RequestParameterName.USERNAME)).thenReturn(username);
        when(req.getParameter(RequestParameterName.PASSWORD)).thenReturn(password);
        when(userDAO.authenticateUser(username, password)).thenThrow(new DAOException(exceptionalMessage));

        expectedException.expect(ActionExecutionException.class);
        expectedException.expectMessage(exceptionalMessage);
        loginAction.execute(req, resp);
    }
}