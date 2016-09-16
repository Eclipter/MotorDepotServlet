<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="bundle.jspf"%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${fn:replace(req.requestURL, fn:substring(req.requestURI, 1,
         fn:length(req.requestURI)), req.contextPath)}" />
<c:set var="currentURL" value="${baseURL.concat('motor_depot?command=get_login_form')}"/>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="login.heading"/></title>
    <link href="css/custom-bootstrap.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
    <script src="js/jquery-2.1.4.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container-fluid">
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="${currentURL}&language=ru"><img class="language-icon" src="images/icon-rus.ico"/></a>
            </li>
            <li>
                <a href="${currentURL}&language=en"><img class="language-icon" src="images/icon-us.ico"/></a>
            </li>
        </ul>
    </div>
</nav>
<div class="container" style="margin-top: 240px">
    <div class="row">
        <div class="col-md-4 col-md-offset-8 col-xs-4 col-xs-offset-8">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <strong><fmt:message key="login.heading"/></strong>
                </div>
                <div class="panel-body">
                    <form method="post" action="motor_depot" class="form-horizontal">
                        <div class="form-group">
                            <label for="inputLogin" class="col-sm-3 control-label"><fmt:message key="login.label.login"/></label>
                            <div class="col-sm-9">
                                <input type="text" name="username" class="form-control" id="inputLogin"
                                       placeholder="<fmt:message key="login.label.login"/>" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword" class="col-sm-3 control-label"><fmt:message key="login.label.password"/></label>
                            <div class="col-sm-9">
                                <input type="password" name="password" class="form-control" id="inputPassword"
                                       placeholder="<fmt:message key="login.label.password"/>" required>
                            </div>
                        </div>
                        <div class="form-group last">
                            <div class="col-sm-offset-3 col-sm-9">
                                <button type="submit" class="btn btn-primary"><fmt:message key="login.button.signin"/></button>
                                <a class="btn btn-default" href="<c:url value="motor_depot"><c:param name="command" value="get_signup_form"/></c:url>">
                                    <fmt:message key="login.button.signup"/>
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