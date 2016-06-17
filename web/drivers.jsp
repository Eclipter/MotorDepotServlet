<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="bundle.jspf" %>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="drivers.heading"/></title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
    <script src="js/jquery-2.1.4.js"></script>
    <script src="js/bootstrap.min.js"></script>
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
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.drivers}" var="driver">
                        <tr>
                            <td>${driver.user.login}</td>
                            <td>
                                <a href="#" data-toggle="popover"
                                   title="<fmt:message key="trucks.modal.label.truck"/> ${driver.truck.id}"
                                   data-trigger="hover" data-content="<fmt:message key="trucks.table.state"/>:
                                   <c:choose>
                                       <c:when test="${driver.truck.state.truckStateName eq 'OK'}">
                                        <fmt:message key="trucks.table.state.ok"/>
                                       </c:when>
                                       <c:when test="${driver.truck.state.truckStateName eq 'BROKEN'}">
                                        <fmt:message key="trucks.table.state.broken"/>
                                       </c:when>
                                       <c:otherwise>
                                        <fmt:message key="trucks.table.state.under_repair"/>
                                       </c:otherwise>
                                   </c:choose>">
                                        ${driver.truck.id}
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
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