<%--
  Created by IntelliJ IDEA.
  User: cui
  Date: 2/12/23
  Time: 12:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post">
    <label for="date">Sort by:</label>
    <select name="date" id="date">
        <option value="newToOld">Newest to Oldest</option>
        <option value="oldToNew">Oldest to Newest</option>
    </select>
    <br>
    <input type="submit" value="Sort" name="filter">

</form>
</body>
</html>
