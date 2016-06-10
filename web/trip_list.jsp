<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="bundle.jspf"%>
<%@ taglib prefix="trtg" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="trips.heading"/></title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
    <script src="js/jquery-2.1.4.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="navbar.jspf"%>
<div class="container">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading">
                <fmt:message key="trips.heading"/>
            </div>
            <div class="panel-body">
                <trtg:triptable trips="${requestScope.trips}"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>