<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 2/9/2026
  Time: 1:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp" %>
<html>
<body>
<c:forEach var="quest" items="${questRepository.all}">
  <a href="play?id=${quest.id}">${quest.name}</a>
</c:forEach>

<a href="/">
  <button>На главную</button>
</a>

</body>
</html>
