<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	
<!DOCTYPE html>
<html lang="de">
<head>
<meta charset="UTF-8">
<link rel='stylesheet' type='text/css' href='style.css' />
<title>Todos anzeigen</title>
</head>
<body>
	<h1>Alle Todos</h1>
	<c:choose>
		<c:when test="${empty todos}">
			Keine Todos gefunden.
		</c:when>
		<c:otherwise>
	<ul>
	<c:forEach items="${todos}" var="todo">
		<c:set var="cssClass"><c:out value="priority-${fn:toLowerCase(todo.priority)}"></c:out></c:set>
		<li class="${cssClass}">
			<c:out value="${todo.id}"/>
			<c:out value="${todo.title}"/>
			<c:if test="${not empty todo.dueDate}">
				<c:out value="(bis ${todo.dueDate})"/>
			</c:if>
		</li>
	</c:forEach>
	</ul>
		</c:otherwise>
	</c:choose>
</body>
</html>
