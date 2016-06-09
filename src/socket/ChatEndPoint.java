package socket;

import bean.UserInfoBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by USER on 30.05.2016.
 */
@ServerEndpoint(value = "/chat", configurator = HttpSessionConfigurator.class)
public class ChatEndPoint {

    private static final Logger logger = LogManager.getLogger();

    private Session session;
    private static final Map<Session, UserInfoBean> loginMap = Collections.synchronizedMap(new HashMap<>());

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        logger.info("chat session opened");
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        UserInfoBean userInfoBean = (UserInfoBean) httpSession.getAttribute("user");
        loginMap.put(session, userInfoBean);
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
                String jsonMessage = createOnlineUsersList();
                session.getBasicRemote().sendText(jsonMessage);
            }
            else if(message.getMessageType() == MessageType.MESSAGE) {
                switch (message.getUsername()) {
                    case "All users":
                        for (Map.Entry<Session, UserInfoBean> entry : loginMap.entrySet()) {
                            entry.getKey().getBasicRemote().sendText(buildMessage(loginMap.get(session).getUserEntity().getLogin(),
                                    message.getMessage()));
                        }
                        break;
                    case "admin":
                        for (Map.Entry<Session, UserInfoBean> entry : loginMap.entrySet()) {
                            if (entry.getValue().isAdmin()) {
                                entry.getKey().getBasicRemote().sendText(buildMessage(loginMap.get(session).getUserEntity().getLogin(),
                                        message.getMessage()));
                            } else if (entry.getKey().equals(session)) {
                                entry.getKey().getBasicRemote().sendText(buildMessage(loginMap.get(session).getUserEntity().getLogin(),
                                        message.getMessage()));
                            }
                        }
                        break;
                    default:
                        for (Map.Entry<Session, UserInfoBean> entry : loginMap.entrySet()) {
                            if (entry.getValue().getUserEntity().getLogin().equals(message.getUsername())) {
                                entry.getKey().getBasicRemote().sendText(buildMessage(loginMap.get(session).getUserEntity().getLogin(),
                                        message.getMessage()));
                            } else if (entry.getKey().equals(session)) {
                                if (entry.getValue().isAdmin()) {
                                    entry.getKey().getBasicRemote().sendText(buildMessageFromAdmin(loginMap.get(session).getUserEntity().getLogin(),
                                            message.getUsername(), message.getMessage()));
                                } else {
                                    entry.getKey().getBasicRemote().sendText(buildMessage(loginMap.get(session).getUserEntity().getLogin(),
                                            message.getMessage()));
                                }
                            }
                        }
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createOnlineUsersList() {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "All users");
        jsonArray.add(jsonObject);
        jsonArray.addAll(loginMap.entrySet().stream().map((Function<Map.Entry<Session, UserInfoBean>, Object>) loginEntry -> {
            JSONObject userJsonObject = new JSONObject();
            userJsonObject.put("name", loginEntry.getValue().getUserEntity().getLogin());
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
        return String.format("%s (%s -> %s): %s", simpleDateFormat.format(new Date()), senderLogin, receiverLogin, message);
    }

    private Message parseJSONMessage(String JSONMessage) {
        JSONParser jsonParser = new JSONParser();
        Message message = new Message();
        try {
            JSONObject object = (JSONObject) jsonParser.parse(JSONMessage);
            String type = (String) object.get("type");
            message.setMessageType(MessageType.valueOf(type.toUpperCase()));
            message.setMessage((String) object.get("message"));
            message.setUsername((String) object.get("to"));
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
