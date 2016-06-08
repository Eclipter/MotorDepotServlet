<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources.jsp_text" />
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="login.heading"/></title>
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
                    <strong><fmt:message key="login.heading"/></strong>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label for="language" class="col-sm-3 control-label"><fmt:message key="login.label.language"/></label>
                            <div class="col-sm-9">
                                <select class="form-control" id="language" name="language" onchange="submit()">
                                    <option value="en" ${language == 'en' ? 'selected' : ''}><fmt:message key="login.select.english"/></option>
                                    <option value="ru" ${language == 'ru' ? 'selected' : ''}><fmt:message key="login.select.russian"/></option>
                                </select>
                            </div>
                        </div>
                    </form>
                    <form method="post" action="motor_depot" class="form-horizontal">
                        <div class="form-group">
                            <label for="inputLogin" class="col-sm-3 control-label"><fmt:message key="login.label.login"/></label>
                            <div class="col-sm-9">
                                <input type="text" name="username" class="form-control" id="inputLogin"
                                       placeholder="Login" required="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword" class="col-sm-3 control-label"><fmt:message key="login.label.password"/></label>
                            <div class="col-sm-9">
                                <input type="password" name="password" class="form-control" id="inputPassword"
                                       placeholder="Password" required="">
                            </div>
                        </div>
                        <div class="form-group last">
                            <div class="col-sm-offset-3 col-sm-9">
                                <button type="submit" class="btn btn-primary"><fmt:message key="login.button.signin"/></button>
                                <a href="<c:url value="motor_depot"><c:param name="command" value="signup_form"/></c:url>">
                                    <button type="button" class="btn btn-default"><fmt:message key="login.button.signup"/></button>
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