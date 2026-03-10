<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quest Pantera</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body class="bg-light">
<my:constants/>
<c:if test="${not empty requestScope[ERROR_MESSAGE_ATTR]}">
    <script>
      alert('${requestScope[ERROR_MESSAGE_ATTR]}');
    </script>
</c:if>
<c:if test="${not empty requestScope[WIN_ATTR]}">
    <script>
      alert('${requestScope[WIN_ATTR]}');
    </script>
</c:if>

<div class="container mt-5">
    <div class="card shadow">
        <div class="card-body text-center">
            <h2>Добро пожаловать, <strong>пользователь</strong>!</h2>

            <hr>

            <div class="mt-4">
                <a href="quest" class="btn btn-primary btn-lg">🎮 Начать квест</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>