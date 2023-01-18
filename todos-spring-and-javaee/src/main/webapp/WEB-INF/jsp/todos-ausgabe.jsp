<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Todos-Anzeige</title>
</head>
<body>
<h1>Anzeige der Todos ${spring ? 'mit Spring' : ''}</h1>
<c:if test="${not empty todos}">
    <ul>
        <c:forEach items="${todos}" var="todo">
            <li><c:out value="${todo.title}"/></li>
        </c:forEach>
    </ul>
</c:if>
</body>
</html>
