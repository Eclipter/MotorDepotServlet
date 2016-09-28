<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="bundle.jspf" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="requests.heading"/></title>
    <link href="../css/custom-bootstrap.css" rel="stylesheet">
    <link href="../css/custom.css" rel="stylesheet">
    <script src="../js/jquery-2.1.4.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="navbar.jspf" %>
<div class="container">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading">
                <c:choose>
                    <c:when test="${sessionScope.user.admin}">
                        <fmt:message key="requests.heading"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="requests.heading.unassigned"/>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="panel-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th><fmt:message key="requests.table.id"/></th>
                        <th><fmt:message key="requests.table.weight"/></th>
                        <th><fmt:message key="requests.table.departure"/></th>
                        <th><fmt:message key="requests.table.destination"/></th>
                        <th><fmt:message key="requests.table.assigned"/></th>
                        <c:if test="${sessionScope.user.admin}">
                            <th><fmt:message key="requests.delete"/></th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.requests}" var="request">
                        <tr>
                            <td>${request.request.id}</td>
                            <td>${request.request.cargoWeight}</td>
                            <td>
                                <a href="#" data-toggle="popover" data-trigger="hover"
                                   title="<fmt:message key="requests.table.hover.address"/>"
                                   data-content="${request.request.departureStation.address}">
                                        ${request.request.departureStation.name}
                                </a>
                            </td>
                            <td>
                                <a href="#" data-toggle="popover" data-trigger="hover"
                                   title="<fmt:message key="requests.table.hover.address"/>"
                                   data-content="${request.request.destinationStation.address}">
                                        ${request.request.destinationStation.name}
                                </a>
                            </td>
                            <c:choose>
                                <c:when test="${request.driver != null}">
                                    <td>
                                        <a href="#" data-toggle="popover"
                                           title="<fmt:message key="requests.table.assigned"/>:"
                                           data-trigger="hover"
                                           data-content="<fmt:message key="requests.table.hover.driver"/> <c:out value="${request.driver.login}"/>">
                                            <span class="glyphicon glyphicon-ok"></span>
                                        </a>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td><span class="glyphicon glyphicon-remove"></span></td>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${sessionScope.user.admin}">
                                <c:choose>
                                    <c:when test="${request.driver == null}">
                                        <td>
                                            <form action="motor_depot" method="post">
                                                <input type="hidden" name="command" value="delete_request">
                                                <input type="hidden" name="request_id" value="${request.request.id}">
                                                <button class="btn btn-danger" type="button" data-toggle="modal"
                                                        data-target="#confirmModal${request.request.id}">
                                                    <span class="glyphicon glyphicon-remove"></span> <fmt:message
                                                        key="requests.delete"/>
                                                </button>
                                                <div id="confirmModal${request.request.id}" class="modal fade" role="dialog">
                                                    <div class="modal-dialog modal-sm">
                                                        <div class="modal-content">
                                                            <div class="modal-body">
                                                                <fmt:message key="requests.are_you_sure"/>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="submit" class="btn btn-primary">
                                                                    <fmt:message
                                                                            key="requests.yes"/></button>
                                                                <button type="button" data-dismiss="modal" class="btn">
                                                                    <fmt:message key="requests.no"/></button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td></td>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:if test="${sessionScope.user.admin}">
                    <button type="button" class="btn btn-primary"
                            data-toggle="modal" data-target="#addRequestModal"><fmt:message
                            key="requests.button.add_request"/></button>
                </c:if>
                <div id="addRequestModal" class="modal fade" role="dialog">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form action="motor_depot" method="post">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title"><fmt:message key="requests.button.add_request"/></h4>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <label for="inputWeight" class="control-label"><fmt:message
                                                key="requests.table.weight"/></label>
                                        <input id="inputWeight" type="text" class="form-control"
                                               placeholder="<fmt:message key="requests.modal.placeholder.weight"/>"
                                               name="cargoWeight" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="departureStationList" class="control-label"><fmt:message
                                                key="requests.table.departure"/>
                                        </label>
                                        <select class="form-control" id="departureStationList"
                                                name="departure_station_id">
                                            <c:forEach items="${requestScope.stations}" var="station">
                                                <option value="${station.id}">
                                                    <fmt:message key="requests.modal.station.name"/>: ${station.name},
                                                    <fmt:message
                                                            key="requests.modal.station.address"/>: ${station.address}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="destinationStationList" class="control-label"><fmt:message
                                                key="requests.table.destination"/>
                                        </label>
                                        <select class="form-control" id="destinationStationList"
                                                name="destination_station_id">
                                            <c:forEach items="${requestScope.stations}" var="station">
                                                <option value="${station.id}">
                                                    <fmt:message key="requests.modal.station.name"/>: ${station.name},
                                                    <fmt:message
                                                            key="requests.modal.station.address"/>: ${station.address}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <input type="hidden" name="command" value="add_request"/>
                                    <button type="submit" class="btn btn-primary"><fmt:message
                                            key="requests.modal.button.add"/></button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel-footer">
                <custom:page_arrows list="${requestScope.requests}" currentURL="${currentURL}"/>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('[data-toggle="popover"]').popover();
    });
</script>
</body>
</html>