<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="bundle.jspf" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="drivers.heading"/></title>
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
                <fmt:message key="drivers.heading"/>
            </div>
            <div class="panel-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th><fmt:message key="drivers.table.login"/></th>
                        <th><fmt:message key="drivers.table.truck_id"/></th>
                        <th><fmt:message key="drivers.table.trips_completed_all"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.drivers}" var="driver">
                        <tr>
                            <td><c:out value="${driver.driver.login}"/></td>
                            <td>
                                <a href="#" data-toggle="popover"
                                   title="<fmt:message key="trucks.modal.label.truck"/> ${driver.driver.truck.id}"
                                   data-trigger="hover" data-content="<fmt:message key="trucks.table.capacity"/>:
                                   ${driver.driver.truck.capacity}">
                                        ${driver.driver.truck.id}
                                </a>
                            </td>
                            <td>
                                ${driver.completedTripsCount} / ${driver.tripsCount}
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="panel-footer">
                <custom:page_arrows list="${requestScope.drivers}" currentURL="${currentURL}"/>
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