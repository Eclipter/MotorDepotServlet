<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="bundle.jspf"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="assignation_form.heading"/></title>
    <link href="css/custom-bootstrap.css" rel="stylesheet">
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
                <fmt:message key="assignation_form.heading"/>
            </div>
            <div class="panel-body">
                <form action="motor_depot" method="post">
                    <div class="form-group">
                        <label for="applicationSelect" class="control-label"><fmt:message key="assignation_form.label.chose_request"/></label>
                        <select class="form-control" id="applicationSelect" name="chosenRequest">
                            <c:forEach items="${requestScope.requests}" var="request">
                                <option value="${request.id}"><fmt:message key="requests.table.id"/>: ${request.id}, <fmt:message key="requests.table.weight"/>: ${request.cargoWeight},
                                    <fmt:message key="requests.table.departure"/>: ${request.departureStation.name} (${request.departureStation.address}),
                                    <fmt:message key="requests.table.destination"/>: ${request.destinationStation.name} (${request.destinationStation.address})
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="driverSelect" class="control-label"><fmt:message key="assignation_form.label.choose_driver"/></label>
                        <select class="form-control" id="driverSelect" name="chosenDriver">
                            <c:forEach items="${requestScope.drivers}" var="driver">
                                <option value="${driver.id}"><fmt:message key="drivers.table.login"/>: <c:out value="${driver.login}"/>, <fmt:message key="assignation_form.select.truck_capacity"/>: ${driver.truck.capacity}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary"><fmt:message key="assignation_form.button.assign"/></button>
                        <input type="hidden" name="command" value="assign_driver_to_a_trip"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>