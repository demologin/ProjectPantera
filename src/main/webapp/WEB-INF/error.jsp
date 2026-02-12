<%--
  Created by IntelliJ IDEA.
  User: mkoda
  Date: 2/11/2026
  Time: 10:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"/><title>Error</title></head>
<body style="font-family: Arial, sans-serif; max-width: 720px; margin: 40px auto;">
<h2>Ошибка</h2>
<p><%= request.getAttribute("errorMessage") %></p>
<a href="<%= request.getContextPath() %>/home">Back</a>
</body>
</html>
