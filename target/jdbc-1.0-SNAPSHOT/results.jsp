<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: cui
  Date: 2/1/23
  Time: 9:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Final Result</title>
</head>
<body>
<c:choose>
    <c:when test="${result}">
        <h1>WIN!</h1>
    </c:when>
    <c:otherwise>
        <h1>Lose!</h1>
    </c:otherwise>
</c:choose>
<a href="/guess">Play again?</a>
</body>
</html>
