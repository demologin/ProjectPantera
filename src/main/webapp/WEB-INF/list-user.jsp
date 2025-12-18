<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>List User</title>
</head>
<body>
<h1>User list</h1>

    <c:forEach var="user" items="${requestScope.users}">
        id=${user.id}. <a href="edit-user?id=${user.id}"> Edit ${user.login}</a><br>
    </c:forEach>

</body>
</html>
