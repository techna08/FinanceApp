<%--
  Created by IntelliJ IDEA.
  User: Nik
  Date: 14.11.2022
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
    <h1>Logs:</h1>
    <c:forEach var="log" items="${requestScope.logs}">
        <div>
                ${log.toString()}
        </div>
    </c:forEach>
</body>
</html>
