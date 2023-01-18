<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Validierungsfehler!</title>
</head>
<body>
<h1>Validierungsfehler!</h1>
<c:if test="${not empty violations}">
    <ul>
        <c:forEach items="${violations}" var="v">
            <li>Property <c:out value="${v.propertyPath}"/>: <c:out value="${v.message}"/></li>
        </c:forEach>
    </ul>
</c:if>
</body>
</html>
