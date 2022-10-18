<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<!DOCTYPE html>
<html lang="de">
<head>
<meta charset="UTF-8">
<link rel='stylesheet' type='text/css' href='style.css' />
<title>Todo einfügen</title>
</head>
<body>
	<h1>Todo einfügen</h1>
	<form:form action="insert" method="post" modelAttribute="newTodo">
		<form:label path="title">Titel:</form:label>
		<form:input path="title" type="text" required="required" maxlength="50"/>
		<form:errors path="title" cssStyle="font-size: 0.9em; color: #cc0000;"/>
		<br/>
		<form:label path="dueDate">Frist:</form:label>
		<form:input path="dueDate" type="date"/>
		<form:errors path="dueDate" cssStyle="font-size: 0.9em; color: #cc0000;"/>
		<br/>
		<input type="submit">
	</form:form>
</body>
</html>
