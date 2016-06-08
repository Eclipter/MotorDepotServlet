<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Водители</title>
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
                Водители
            </div>
            <div class="panel-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Логин</th>
                        <th>Id автомобиля</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${drivers}" var="driver">
                        <tr>
                            <td>${driver.userByUserId.login}</td>
                            <td>
                                <a href="#" data-toggle="popover" title="Truck ${driver.truckByTruckId.id}" data-trigger="hover" data-content="State: ${driver.truckByTruckId.stateByStateId.stateName}, Capacity: ${driver.truckByTruckId.capacity}">
                                    ${driver.truckByTruckId.id}
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
    $(document).ready(function(){
        $('[data-toggle="popover"]').popover();
    });
</script>
</body>
</html>