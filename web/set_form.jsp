<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Назначить на рейс</title>
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
                Назначить водителя на рейс
            </div>
            <div class="panel-body">
                <form action="motor_depot" method="post">
                    <div class="form-group">
                        <label for="applicationSelect" class="control-label">Выберите заявку</label>
                        <select class="form-control" id="applicationSelect" name="chosenApplication">
                            <c:forEach items="${applications}" var="application">
                                <option value="${application.id}">Id: ${application.id}, вес груза: ${application.cargoWeight}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="driverSelect" class="control-label">Выберите водителя</label>
                        <select class="form-control" id="driverSelect" name="chosenDriver">
                            <c:forEach items="${drivers}" var="driver">
                                <option value="${driver.userId}">Id: ${driver.userId}, Грузоподъёмность автомобиля: ${driver.truckByTruckId.capacity}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Назначить</button>
                        <input type="hidden" name="command" value="set_driver_on_trip"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>