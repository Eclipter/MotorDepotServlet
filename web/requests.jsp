<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Заявки</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="navbar.jsp"%>
<div class="container">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading">
                Заявки
            </div>
            <div class="panel-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Вес груза</th>
                        <th>Распределена</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requests}" var="request">
                        <tr>
                            <td>${request.requestEntity.id}</td>
                            <td>${request.requestEntity.cargoWeight}</td>
                            <c:choose>
                                <c:when test="${request.driverEntity != null}">
                                    <td>
                                        <a href="#" data-toggle="popover" title="Распределена:" data-trigger="hover" data-content="Водитель ${request.driverEntity.userId}">
                                            <span class="glyphicon glyphicon-ok"></span>
                                        </a>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td><span class="glyphicon glyphicon-remove"></span></td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function(){
        $('[data-toggle="popover"]').popover();
    });
</script>
</body>
</html>