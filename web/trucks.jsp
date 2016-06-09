<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Автомобили</title>
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
                Автомобили
            </div>
            <div class="panel-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Грузоподъёмность</th>
                        <th>Состояние</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${trucks}" var="car">
                        <tr>
                            <td>${car.id}</td>
                            <td>${car.capacity}</td>
                            <td>${car.stateByStateId.stateName}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">Изменить состояние автомобиля</button>
                <div id="myModal" class="modal fade" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <form action="motor_depot" method="post">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Изменить состояние автомобиля</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <label for="carList">Автомобиль</label>
                                        <select class="form-control" id="carList" name="chosenTruck">
                                            <c:forEach items="${trucks}" var="car">
                                                <option value="${car.id}">Id: ${car.id}, Грузоподъёмность: ${car.capacity}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="stateList">Состояние</label>
                                        <select class="form-control" id="stateList" name="chosenState">
                                            <option value="OK">В порядке</option>
                                            <option value="BROKEN">Сломана</option>
                                            <option value="UNDER_REPAIR">На ремонте</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-default">Сохранить</button>
                                    <input type="hidden" name="command" value="change_truck_state"/>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>