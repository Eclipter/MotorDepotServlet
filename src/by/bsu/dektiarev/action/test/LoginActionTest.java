package by.bsu.dektiarev.action.test;

import by.bsu.dektiarev.action.Action;
import by.bsu.dektiarev.action.LoginAction;
import by.bsu.dektiarev.bean.UserInfoBean;
import by.bsu.dektiarev.dao.DriverDAO;
import by.bsu.dektiarev.dao.UserDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import by.bsu.dektiarev.util.RequestParameterName;
import by.bsu.dektiarev.util.URLConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by USER on 21.06.2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({DAOFactory.class, LogManager.class})
public class LoginActionTest {

    @Mock
    private Logger logger;

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private HttpSession session;

    @Mock
    private UserDAO userDAO;

    @Mock
    private DriverDAO driverDAO;

    @Mock
    private UserInfoBean userInfoBean;

    @InjectMocks
    Action loginAction = new LoginAction();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(DAOFactory.class);
        PowerMockito.mock(LogManager.class);
        PowerMockito.when(LogManager.getLogger()).thenReturn(logger);
        PowerMockito.when(DAOFactory.getDAOFromFactory(DAOType.USER)).thenReturn(userDAO);
        PowerMockito.when(DAOFactory.getDAOFromFactory(DAOType.DRIVER)).thenReturn(driverDAO);
        when(req.getSession()).thenReturn(session);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void executeTestAdminWithCorrectLoginPass() throws Exception {
        String username = "newuser";
        String password = "newuser";
        when(req.getParameter(RequestParameterName.USERNAME)).thenReturn(username);
        when(req.getParameter(RequestParameterName.PASSWORD)).thenReturn(password);
        User confirmedUser = new User();
        when(userDAO.authenticateUser(username, password)).thenReturn(confirmedUser);
        when(driverDAO.searchByUser(confirmedUser)).thenReturn(null);
        String answer = loginAction.execute(req, resp);
        assertTrue(userInfoBean.isAdmin());
        assertEquals(answer, URLConstant.GET_INDEX_PAGE);
    }

}