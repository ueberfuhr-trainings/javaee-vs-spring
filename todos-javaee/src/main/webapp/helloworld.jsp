<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <title>Hello World</title>
</head>
<body>
<h1>Hello <%= request.getParameter("name") %>.</h1>
<%
    String text = request.getParameter("name");
    if (null == text) {
        text = "World";
    }
    out.println("Hello " + text);
%>
<br>
Hello ${ empty param.name ? 'World' : param.name }
</body>
</html>
