<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="bundle.jspf"%>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="index.text.heading"/></title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
    <script src="js/jquery-2.1.4.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="navbar.jspf" %>
<div class="container">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-body">
                <c:choose>
                    <c:when test="${sessionScope.user.admin}">
                        <header>
                            <h1><fmt:message key="index.text.hello"/>, <fmt:message key="index.text.admin"/>
                                    <c:out value="${sessionScope.user.user.login}"/>!</h1>
                        </header>
                        <article>
                            <p>
                                <fmt:message key="index.welcomeMessage.admin"/>
                            </p>
                        </article>
                    </c:when>
                    <c:otherwise>
                        <header>
                            <h1><fmt:message key="index.text.hello"/>, <fmt:message key="index.text.driver"/>
                                <c:out value="${sessionScope.user.user.login}"/>!</h1>
                        </header>
                        <article>
                            <p>
                                <fmt:message key="index.welcomeMessage.driver"/>
                            </p>
                        </article>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
</body>
</html>