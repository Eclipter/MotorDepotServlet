<%@ tag body-content="empty" pageEncoding="UTF-8" %>
<%@ attribute name="trips" required="true" rtexprvalue="true" type="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table class="table">
    <thead>
    <tr>
        <th>Id</th>
        <th>Заявка</th>
        <th>Водитель</th>
        <th>Выполнена</th>
        <c:if test="${sessionScope.user.admin}">
            <th>Редактирование</th>
        </c:if>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${trips}" var="trip">
        <tr>
            <td>${trip.id}</td>
            <td>Id: ${trip.requestByRequestId.id}, груз: ${trip.requestByRequestId.cargoWeight}</td>
            <td>Id: ${trip.driverByDriverUserId.userId}, имя: ${trip.driverByDriverUserId.userByUserId.login}</td>
            <c:choose>
                <c:when test="${trip.isComplete}">
                    <td><span class="glyphicon glyphicon-ok"></span></td>
                </c:when>
                <c:otherwise>
                    <td><span class="glyphicon glyphicon-remove"></span></td>
                </c:otherwise>
            </c:choose>
            <c:if test="${sessionScope.user.admin}">
                <td><button type="submit" class="btn btn-default" data-toggle="modal" data-target="#modal${trip.id}">Изменить состояние</button></td>
                <div id="modal${trip.id}" class="modal fade" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <form action="motor_depot" method="POST">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Изменить состояние рейса</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        Рейс номер ${trip.id}, автомобиль номер ${trip.driverByDriverUserId.truckByTruckId.id}, водитель: ${trip.driverByDriverUserId.userByUserId.login}
                                    </p>
                                    <p>
                                        <div class="form-group">
                                            <label for="stateList">Состояние рейса</label>
                                            <select class="form-control" id="stateList" name="chosenState">
                                                <option value="true">Выполнен</option>
                                                <option value="false">Не выполнен</option>
                                            </select>
                                        </div>
                                    </p>
                                </div>
                                <div class="modal-footer">
                                    <input type="hidden" name="trip_id" value="${trip.id}"/>
                                    <input type="hidden" name="command" value="change_trip_state">
                                    <button type="submit" class="btn btn-primary">Сохранить</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>