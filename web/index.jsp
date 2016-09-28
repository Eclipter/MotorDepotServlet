<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
</head>
<body>
<c:choose>
    <c:when test="${empty sessionScope.user}">
        <jsp:forward page="pages/login.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:forward page="pages/main_page.jsp"/>
    </c:otherwise>
</c:choose>
</body>
</html>