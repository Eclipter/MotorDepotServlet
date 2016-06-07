<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Вход в систему</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<div class="container" style="margin-top: 240px">
    <div class="row">
        <div class="col-md-4 col-md-offset-8 col-xs-4 col-xs-offset-8">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <strong>Вход</strong>
                </div>
                <div class="panel-body">
                    <form method="post" action="motor_depot" class="form-horizontal">
                        <div class="form-group">
                            <label for="inputLogin" class="col-sm-3 control-label">Логин</label>
                            <div class="col-sm-9">
                                <input type="text" name="username" class="form-control" id="inputLogin"
                                       placeholder="Login" required="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword" class="col-sm-3 control-label">Пароль</label>
                            <div class="col-sm-9">
                                <input type="password" name="password" class="form-control" id="inputPassword"
                                       placeholder="Password" required="">
                            </div>
                        </div>
                        <div class="form-group last">
                            <div class="col-sm-offset-3 col-sm-9">
                                <button type="submit" class="btn btn-primary">Войти</button>
                                <a href="<c:url value="motor_depot"><c:param name="command" value="signup_form"/></c:url>">
                                    <button type="button" class="btn btn-default">Зарегистрироваться</button>
                                </a>
                            </div>
                        </div>
                        <input type="hidden" name="command" value="login"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>