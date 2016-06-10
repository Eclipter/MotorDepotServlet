<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="bundle.jspf"%>
<html>
<head>
    <title>Ошибка</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
    <script src="js/jquery-2.1.4.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-static-top">
</nav>
<div class="container">
    <div class="row">
        <div class="panel panel-danger">
            <div class="panel-heading">
                Ошибка
            </div>
            <div class="panel-body">
                <h3>${errorMessage}</h3>
            </div>
        </div>
    </div>
</div>
</body>
</html>
