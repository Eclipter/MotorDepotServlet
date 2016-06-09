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
                <c:choose>
                    <c:when test="${sessionScope.user.admin}">
                        Заявки
                    </c:when>
                    <c:otherwise>
                        Нараспределённые заявки
                    </c:otherwise>
                </c:choose>
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
                                        <a href="#" data-toggle="popover" title="Распределена:"
                                           data-trigger="hover" data-content="Водитель ${request.driverEntity.userId}">
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
                <c:if test="${sessionScope.user.admin}">
                    <button type="button" class="btn btn-primary"
                            data-toggle="modal" data-target="#myModal">Добавить заявку</button>
                </c:if>
                <div id="myModal" class="modal fade" role="dialog">
                    <div class="modal-dialog modal-sm">
                        <div class="modal-content">
                            <form action="motor_depot" METHOD="post">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Добавить заявку</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <label for="inputWeight" class="control-label">Вес груза</label>
                                        <input id="inputWeight" type="text" class="form-control" placeholder="Weight"
                                               name="cargoWeight" required>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <input type="hidden" name="command" value="add_request"/>
                                    <button type="submit" class="btn btn-primary">Добавить</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
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