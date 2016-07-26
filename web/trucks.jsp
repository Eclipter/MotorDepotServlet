<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="bundle.jspf"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="trucks.heading"/></title>
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
                <fmt:message key="trucks.heading"/>
            </div>
            <div class="panel-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th><fmt:message key="trucks.table.id"/></th>
                        <c:if test="${sessionScope.user.admin}">
                            <th><fmt:message key="trucks.table.number"/></th>
                        </c:if>
                        <th><fmt:message key="trucks.table.capacity"/></th>
                        <th><fmt:message key="trucks.table.state"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.trucks}" var="car">
                        <tr>
                            <td>${car.id}</td>
                            <c:if test="${sessionScope.user.admin}">
                                <td><c:out value="${car.number}"/></td>
                            </c:if>
                            <td>${car.capacity}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${car.state.truckStateName eq 'OK'}">
                                        <fmt:message key="trucks.table.state.ok"/>
                                    </c:when>
                                    <c:when test="${car.state.truckStateName eq 'BROKEN'}">
                                        <fmt:message key="trucks.table.state.broken"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="trucks.table.state.under_repair"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:if test="${!sessionScope.user.admin}">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#changeStateModal"><fmt:message key="trucks.button.change_state"/></button>
                    <div id="changeStateModal" class="modal fade" role="dialog">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <form action="motor_depot" method="post">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title"><fmt:message key="trucks.button.change_state"/></h4>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-group">
                                            <label for="stateList"><fmt:message key="trucks.table.state"/></label>
                                            <select class="form-control" id="stateList" name="chosenState">
                                                <option value="OK"><fmt:message key="trucks.table.state.ok"/></option>
                                                <option value="BROKEN"><fmt:message key="trucks.table.state.broken"/></option>
                                                <option value="UNDER_REPAIR"><fmt:message key="trucks.table.state.under_repair"/></option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-default"><fmt:message key="trucks.modal.button.save"/></button>
                                        <input type="hidden" name="command" value="change_truck_state"/>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>