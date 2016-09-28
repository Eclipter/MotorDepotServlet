<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="bundle.jspf"%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${fn:replace(req.requestURL, fn:substring(req.requestURI, 1,
         fn:length(req.requestURI)), req.contextPath)}" />
<c:set var="currentURL" value="${baseURL}motor_depot?command=get_signup_form"/>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="signup.heading"/></title>
    <link href="../css/custom-bootstrap.css" rel="stylesheet">
    <link href="../css/custom.css" rel="stylesheet">
    <script src="../js/jquery-2.1.4.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-static-top">
    <ul class="nav navbar-nav">
        <li>
            <a href="<c:url value="motor_depot"><c:param name="command" value="get_login_form"/></c:url>">
                <span class="glyphicon glyphicon-arrow-left"></span> <fmt:message key="signup.label.back"/>
            </a>
        </li>
    </ul>
    <div class="container-fluid">
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="${currentURL}&language=ru"><img class="language-icon" src="../images/icon-rus.ico"/></a>
            </li>
            <li>
                <a href="${currentURL}&language=en"><img class="language-icon" src="../images/icon-us.ico"/></a>
            </li>
        </ul>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading">
                <strong><fmt:message key="signup.heading"/></strong>
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
                        <label for="inputUsername" class="control-label"><fmt:message key="signup.label.username"/></label>
                        <input type="text" value="${fn:escapeXml(param.username)}" name="username" id="inputUsername" class="form-control" placeholder="<fmt:message key="signup.placeholder.login"/>" required>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword" class="control-label"><fmt:message key="signup.label.password"/></label>
                        <input type="password" name="password" id="inputPassword" class="form-control" placeholder="<fmt:message key="signup.placeholder.password"/>" required>
                    </div>
                    <div class="form-group">
                        <label for="inputPasswordRepeat" class="control-label"><fmt:message key="signup.label.password.repeat"/></label>
                        <input type="password" name="passwordRepeat" id="inputPasswordRepeat" class="form-control" placeholder="<fmt:message key="signup.placeholder.password"/>" required>
                    </div>
                    <div class="form-group">
                        <label for="inputTruckNumber" class="control-label"><fmt:message key="signup.label.number"/></label>
                        <input type="text" value="${fn:escapeXml(param.truckNumber)}" name="truckNumber" id="inputTruckNumber" class="form-control" placeholder="<fmt:message key="signup.placeholder.number"/>" required>
                    </div>
                    <div class="form-group">
                        <label for="inputTruckCapacity" class="control-label"><fmt:message key="signup.label.capacity"/></label>
                        <input type="text" name="truckCapacity" id="inputTruckCapacity" class="form-control" placeholder="<fmt:message key="signup.placeholder.capacity"/>" required>
                    </div>
                    <div class="form-group last">
                        <button type="submit" class="btn btn-primary"><fmt:message key="signup.button.signup"/></button>
                        <button type="reset" class="btn btn-default"><fmt:message key="signup.button.reset"/></button>
                    </div>
                    <input type="hidden" name="command" value="signup"/>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
