<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Добро пожаловать!</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="navbar.jsp"%>
<div class="container">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-body">
                <header>
                    <c:choose>
                        <c:when test="${sessionScope.user.admin}">
                            <c:set var="role" value="администратор"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="role" value="водитель"/>
                        </c:otherwise>
                    </c:choose>
                    <h1>Здравствуйте, ${role} ${sessionScope.user.userEntity.login}!</h1>
                </header>
                <article class="row">
                    <p class="col-lg-9">
                        Вы находитесь на сайте автобазы №1488 г.Минска.
                    </p>
                    <p class="col-lg-9">
                        Здесь Вы можете контролировать рейсы, назначать заявки, осуществлять мониторинг автомобилей и
                        водителей.
                    </p>
                </article>
            </div>
        </div>
    </div>
</div>
</body>
</html>