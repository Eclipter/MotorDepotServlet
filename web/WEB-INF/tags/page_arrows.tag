<%@ tag body-content="empty" pageEncoding="UTF-8" %>
<%@ attribute name="list" required="true" rtexprvalue="true" type="java.util.List" %>
<%@ attribute name="currentURL" required="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${empty requestScope.startFrom or requestScope.startFrom < 0}">
    <c:set var="startFrom" value="0"/>
</c:if>
<a href="${currentURL}&startFrom=${startFrom - applicationScope.fetchLimit}">
    <span class="glyphicon glyphicon-chevron-left"></span>
</a> ${startFrom + 1} - ${startFrom + fn:length(list)}
<a href="${currentURL}&startFrom=${startFrom + fn:length(list)}">
    <span class="glyphicon glyphicon-chevron-right"></span>
</a>