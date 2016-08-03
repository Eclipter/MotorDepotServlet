<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="bundle.jspf" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title><fmt:message key="chat.heading"/></title>
    <link href="css/custom-bootstrap.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
    <script src="js/jquery-2.1.4.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
        var chat;
        function connect() {
            socket = new WebSocket("ws://localhost:9090/chat");
            socket.onmessage = function (event) {
                console.log(event.data);

                try {
                    var message = JSON.parse(event.data);
                } catch (e) {
                    document.getElementById('chatArea').value += '\n' + event.data;
                    return;
                }

                $('#users').empty();
                message.forEach(function (item, i, arr) {
                    var radio = document.createElement("div");
                    radio.setAttribute("class", "radio");
                    var label = document.createElement("label");
                    var button = document.createElement("input");
                    button.setAttribute("type", "radio");
                    button.setAttribute("name", "userRadio");
                    var value = document.createTextNode(item["name"]);
                    label.appendChild(button);
                    label.appendChild(value);
                    radio.appendChild(label);
                    $('#users').append(radio);
                    $(radio).click(function () {
                        var name = $(this).children("label")[0].innerText;
                        $('#receiver').attr("value", name);
                    });
                });
            };
        }

        function getOnlineUsers() {
            var jsonMessage = {
                type: "get_online_users",
                message: "getUsers",
                to: "server"
            };
            socket.send(JSON.stringify(jsonMessage));
        }

        function sendMessage() {
            var message = document.getElementById('message').value;
            if (message === "") {
                return;
            }
            var jsonMsg = {
                type: "message",
                message: message,
                to: document.getElementById('receiver').getAttribute("value")
            };
            socket.send(JSON.stringify(jsonMsg));
            document.getElementById('message').value = "";
        }
        window.addEventListener('load', connect);

        document.getElementById("message")
                .addEventListener("keyup", function (event) {
                    event.preventDefault();
                    if (event.keyCode == 13) {
                        document.getElementById("sendButton").click();
                    }
                });
    </script>
</head>
<body>
<%@include file="navbar.jspf" %>
<div class="container">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3><fmt:message key="chat.panel.heading"/></h3>
            </div>
            <div class="panel-body">

                <c:choose>
                    <c:when test="${sessionScope.user.admin}">
                        <input type="hidden" id="receiver" value="All users"/>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" id="receiver" value="admin"/>
                    </c:otherwise>
                </c:choose>

                <c:if test="${sessionScope.user.admin}">
                    <div class="form-group">
                        <button class="btn btn-info"
                                onclick="getOnlineUsers()"><fmt:message key="chat.button.online_users"/></button>
                    </div>
                    <div class="form-group">
                        <form id="users" role="form">
                        </form>
                    </div>
                </c:if>

                <div class="form-group">
                    <label for="chatArea"><fmt:message key="chat.label.chat"/></label>
                    <textarea id="chatArea" rows="10" class="form-control chat-area" disabled></textarea>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <input type="text" id="message" class="form-control" placeholder="Сообщение" required/>
                    <span class="input-group-btn">
                        <button id="sendButton" class="btn btn-primary" type="button"
                                onclick="sendMessage()"><fmt:message key="chat.button.send"/></button>
                    </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
