<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Hero-Features-icons.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/Bold-BS4-Full-Page-Image-Header.css'/>">

<html>
<head>
    <title>Error</title>
</head>
<body>
<div style="margin: 100px">
  <h2 class="mb-4">Ошибка!</h2>

  <c:if test="${not empty sessionScope.error}">
    <div class="alert alert-danger">
        ${sessionScope.error}
    </div>
  </c:if>

  <div class="button-group">
    <form method="GET" action="<c:url value='/home'/>" style="display:inline; margin-bottom: 10px">
      <input type="hidden" name="questId" value="${requestScope.quest.id}"/>
      <button type="submit" class="btn btn-primary fs-5 me-2 px-4 py-2">
        Вернуться на главную
      </button>
    </form>

    <form method="GET" action="<c:url value='/edit-quest'/>">
      <button type="submit" class="btn btn-outline-primary fs-5 px-4 py-2">
        Создать или отредактировать квест
      </button>
    </form>
  </div>

</div>

</body>
</html>
