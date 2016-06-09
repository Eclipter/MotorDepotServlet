<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="<c:url value="motor_depot"><c:param name="command" value="get_index_page"/></c:url>">Главная</a>
        </div>
        <ul class="nav navbar-nav">
            <c:if test="${sessionScope.user.admin}">
                <li>
                    <a href="<c:url value="motor_depot"><c:param name="command" value="get_trucks"/></c:url>"><span class="glyphicon glyphicon-bed"></span> Автомобили</a>
                </li>
            </c:if>
            <li>
                <a href="<c:url value="motor_depot"><c:param name="command" value="get_drivers"/></c:url>"><span class="glyphicon glyphicon-user"></span> Водители</a>
            </li>
            <li>
                <c:choose>
                    <c:when test="${sessionScope.user.admin}">
                        <a href="<c:url value="motor_depot"><c:param name="command" value="get_requests"/></c:url>"><span class="glyphicon glyphicon-list-alt"></span> Заявки</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value="motor_depot"><c:param name="command" value="get_unset_requests"/></c:url>"><span class="glyphicon glyphicon-list-alt"></span> Заявки</a>
                    </c:otherwise>
                </c:choose>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <span class="glyphicon glyphicon-road"></span> Рейсы
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <c:choose>
                            <c:when test="${sessionScope.user.admin}">
                                <a href="<c:url value="motor_depot"><c:param name="command" value="get_trips"/></c:url>">Список рейсов</a>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="motor_depot"><c:param name="command" value="get_trips_by_driver"/></c:url>">Список рейсов</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <c:if test="${sessionScope.user.admin}">
                        <li>
                            <a href="<c:url value="motor_depot"><c:param name="command" value="get_setting_form"/></c:url>">Назначить на рейс</a>
                        </li>
                    </c:if>
                </ul>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="<c:url value="motor_depot"><c:param name="command" value="chat"/></c:url>"><span class="glyphicon glyphicon-envelope"></span> Чат с администратором</a>
            </li>
            <li>
                <a href="<c:url value="motor_depot"><c:param name="command" value="logout"/></c:url>"><span class="glyphicon glyphicon-log-out"></span> Выход</a>
            </li>
        </ul>
    </div>
</nav>
