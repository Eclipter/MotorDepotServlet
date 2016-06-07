<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Зарегистрироваться</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-static-top">
    <ul class="nav navbar-nav">
        <li>
            <a href="login.jsp">
                <span class="glyphicon glyphicon-arrow-left"></span> Назад
            </a>
        </li>
    </ul>
</nav>
<div class="container">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading">
                <strong>Регистрация</strong>
            </div>
            <div class="panel-body">
                <c:if test="${sessionScope.errorMessage != null}">
                    <div class="alert alert-danger">
                        <c:out value="${sessionScope.errorMessage}"/>
                        <c:remove var="errorMessage" scope="session"/>
                    </div>
                </c:if>
                <form action="motor_depot" method="post">
                    <div class="form-group">
                        <label for="inputUsername" class="control-label">Введите имя пользователя</label>
                        <input type="text" name="username" id="inputUsername" class="form-control" placeholder="Login" required>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword" class="control-label">Введите пароль</label>
                        <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
                    </div>
                    <div class="form-group">
                        <label for="inputPasswordRepeat" class="control-label">Повторите пароль</label>
                        <input type="password" name="passwordRepeat" id="inputPasswordRepeat" class="form-control" placeholder="Password" required>
                    </div>
                    <div class="form-group">
                        <label for="inputTruckCapacity" class="control-label">Введите грузоподъёмность автомобиля</label>
                        <input type="text" name="truckCapacity" id="inputTruckCapacity" class="form-control" placeholder="Capacity" required>
                    </div>
                    <div class="form-group last">
                        <button type="submit" class="btn btn-primary" <%--onclick="<c:set var="firstEnter" scope="session" value="false"/>"--%>>Зарегистрироваться</button>
                        <button type="reset" class="btn btn-default">Стереть</button>
                    </div>
                    <input type="hidden" name="command" value="signup"/>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
