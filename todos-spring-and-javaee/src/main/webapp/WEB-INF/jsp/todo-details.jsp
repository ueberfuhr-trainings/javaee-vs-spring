<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Todo-Details</title>
</head>
<body>
<h1>Todo-Details</h1>
<ul>
    <li>ID: <c:out value="${todo.id}"/></li>
    <li>Titel: <c:out value="${todo.title}"/></li>
    <li>Frist: <c:out value="${todo.dueDate}"/></li>
    <li>Priorität: <c:out value="${todo.priority}"/></li>
</ul>
</body>
</html>
