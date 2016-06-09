<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="trtg" tagdir="/WEB-INF/tags" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Список рейсов</title>
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
            <div class="panel-heading">
                Список рейсов
            </div>
            <div class="panel-body">
                <trtg:triptable trips="${trips}"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>