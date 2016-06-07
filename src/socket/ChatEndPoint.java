package socket;

import action.controller.UserController;
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

    private Session session;
    private static Map<Session, UserController> loginMap = Collections.synchronizedMap(new HashMap<>());

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        UserController userController = (UserController) httpSession.getAttribute("user");
        loginMap.put(session, userController);
    }

    @OnClose
    public void onClose(Session session) {
        loginMap.remove(session);
    }

    @OnMessage
    public void onMessage(String JSONMessage, Session session) {
        try {
            Message message = parseJSONMessage(JSONMessage);
            if(message.getMessageType() == MessageType.GET_ONLINE_USERS) {
                String jsonMessage = createOnlineUsersList();
                session.getBasicRemote().sendText(jsonMessage);
            }
            else if(message.getMessageType() == MessageType.MESSAGE) {
                if(message.getUsername().equals("All users")) {
                    for(Map.Entry<Session, UserController> entry : loginMap.entrySet()) {
                        entry.getKey().getBasicRemote().sendText(buildMessage(loginMap.get(session).getUserEntity().getLogin(),
                                message.getMessage()));
                    }
                }
                else if(message.getUsername().equals("admin")) {
                    for(Map.Entry<Session, UserController> entry : loginMap.entrySet()) {
                        if(entry.getValue().isAdmin()) {
                            entry.getKey().getBasicRemote().sendText(buildMessage(loginMap.get(session).getUserEntity().getLogin(),
                                    message.getMessage()));
                        }
                        else if(entry.getKey().equals(session)) {
                            entry.getKey().getBasicRemote().sendText(buildMessage(loginMap.get(session).getUserEntity().getLogin(),
                                    message.getMessage()));
                        }
                    }
                }
                else {
                    for(Map.Entry<Session, UserController> entry : loginMap.entrySet()) {
                        if(entry.getValue().getUserEntity().getLogin().equals(message.getUsername())) {
                            entry.getKey().getBasicRemote().sendText(buildMessage(loginMap.get(session).getUserEntity().getLogin(),
                                    message.getMessage()));
                        }
                        else if(entry.getKey().equals(session)) {
                            if(entry.getValue().isAdmin()) {
                                entry.getKey().getBasicRemote().sendText(buildMessageFromAdmin(loginMap.get(session).getUserEntity().getLogin(),
                                        message.getUsername(), message.getMessage()));
                            }
                            else {
                                entry.getKey().getBasicRemote().sendText(buildMessage(loginMap.get(session).getUserEntity().getLogin(),
                                        message.getMessage()));
                            }
                        }
                    }
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
        jsonArray.addAll(loginMap.entrySet().stream().map((Function<Map.Entry<Session, UserController>, Object>) loginEntry -> {
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
