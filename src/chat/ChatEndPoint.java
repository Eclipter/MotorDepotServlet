package chat;

import bean.UserInfoBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import util.BundleName;
import util.InternationalizedBundleManager;
import util.RequestParameterName;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by USER on 30.05.2016.
 */
@ServerEndpoint(value = "/chat", configurator = HttpSessionConfigurator.class)
public class ChatEndPoint {

    private static final Logger logger = LogManager.getLogger();
    private static final Map<Session, HttpSession> loginMap = new ConcurrentHashMap<>();
    private static final String ALL_USERS_BUNDLE_KEY = "chat.radio.all_users";
    private static final String NO_ADMIN_BUNDLE_KEY = "chat.warning.no_admin";
    private static final String NO_USER_BUNDLE_KEY = "chat.warning.no_user";

    private Session session;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        logger.info("chat session opened");
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        loginMap.put(session, httpSession);
    }

    @OnClose
    public void onClose(Session session) {
        loginMap.remove(session);
        logger.info("chat session closed");
    }

    @OnMessage
    public void onMessage(String JSONMessage, Session session) {
        logger.info("incoming message");
        try {
            Message message = parseJSONMessage(JSONMessage);
            if(message.getMessageType() == MessageType.GET_ONLINE_USERS) {
                String jsonMessage = createOnlineUsersList(loginMap.get(session));
                session.getBasicRemote().sendText(jsonMessage);
            }
            else if(message.getMessageType() == MessageType.MESSAGE) {
                UserInfoBean sender = (UserInfoBean)
                        loginMap.get(session).getAttribute(RequestParameterName.USER);
                switch (message.getUsername()) {
                    case JSONMessageParameter.ALL_USERS_ENGLISH:
                    case JSONMessageParameter.ALL_USERS_RUSSIAN:
                        for (Map.Entry<Session, HttpSession> entry : loginMap.entrySet()) {
                            entry.getKey().getBasicRemote().sendText(buildMessage(sender.getUserEntity().getLogin(),
                                    message.getMessage()));
                        }
                        break;
                    case JSONMessageParameter.ADMIN:
                        Session adminSession = null;
                        for (Map.Entry<Session, HttpSession> entry : loginMap.entrySet()) {
                            UserInfoBean user = (UserInfoBean)
                                    entry.getValue().getAttribute(RequestParameterName.USER);
                            if (user.isAdmin()) {
                                adminSession = entry.getKey();
                                break;
                            }
                        }
                        if (adminSession != null) {
                            adminSession.getBasicRemote().sendText(buildMessage(sender.getUserEntity().getLogin(),
                                    message.getMessage()));
                            session.getBasicRemote().sendText(buildMessage(sender.getUserEntity().getLogin(),
                                    message.getMessage()));
                        } else {
                            session.getBasicRemote().sendText(
                                    InternationalizedBundleManager.getProperty(BundleName.JSP_TEXT, NO_ADMIN_BUNDLE_KEY,
                                            (String) loginMap.get(session).getAttribute(RequestParameterName.LANGUAGE)));
                        }
                        break;
                    default:
                        boolean userFound = false;
                        for (Map.Entry<Session, HttpSession> entry : loginMap.entrySet()) {
                            UserInfoBean user = (UserInfoBean)
                                    entry.getValue().getAttribute(RequestParameterName.USER);
                            if (user.getUserEntity().getLogin().equals(message.getUsername())) {
                                entry.getKey().getBasicRemote().sendText(
                                        buildMessage(sender.getUserEntity().getLogin(), message.getMessage()));
                                userFound = true;
                                break;
                            }
                        }
                        if (userFound) {
                            if (sender.isAdmin()) {
                                session.getBasicRemote().sendText(
                                        buildMessageFromAdmin(sender.getUserEntity().getLogin(),
                                                message.getUsername(), message.getMessage()));
                            } else {
                                session.getBasicRemote().sendText(
                                        buildMessage(sender.getUserEntity().getLogin(), message.getMessage()));
                            }
                        } else {
                            session.getBasicRemote().sendText(
                                    InternationalizedBundleManager.getProperty(BundleName.JSP_TEXT, NO_USER_BUNDLE_KEY,
                                            (String) loginMap.get(session).getAttribute(RequestParameterName.LANGUAGE)));
                        }
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createOnlineUsersList(HttpSession session) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSONMessageParameter.NAME,
                InternationalizedBundleManager.getProperty(BundleName.JSP_TEXT, ALL_USERS_BUNDLE_KEY,
                (String) session.getAttribute(RequestParameterName.LANGUAGE)));
        jsonArray.add(jsonObject);
        jsonArray.addAll(loginMap.entrySet().stream().map((Function<Map.Entry<Session, HttpSession>, Object>)
                loginEntry -> {
            JSONObject userJsonObject = new JSONObject();
            UserInfoBean user = (UserInfoBean)
                    loginEntry.getValue().getAttribute(RequestParameterName.USER);
            userJsonObject.put(JSONMessageParameter.NAME, user.getUserEntity().getLogin());
            return userJsonObject;
        }).collect(Collectors.toList()));
        return jsonArray.toJSONString();
    }

    private String buildMessage(String senderLogin, String message) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return String.format("%s (%s): %s", simpleDateFormat.format(new Date()), senderLogin, message);
    }

    private String buildMessageFromAdmin(String senderLogin, String receiverLogin, String message) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return String.format("%s (%s -> %s): %s", simpleDateFormat.format(new Date()),
                senderLogin, receiverLogin, message);
    }

    private Message parseJSONMessage(String JSONMessage) {
        JSONParser jsonParser = new JSONParser();
        Message message = new Message();
        try {
            JSONObject object = (JSONObject) jsonParser.parse(JSONMessage);
            String type = (String) object.get(JSONMessageParameter.TYPE);
            message.setMessageType(MessageType.valueOf(type.toUpperCase()));
            message.setMessage((String) object.get(JSONMessageParameter.MESSAGE));
            message.setUsername((String) object.get(JSONMessageParameter.TO));
            return message;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return message;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
