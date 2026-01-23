<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quest</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow login-card">
        <div class="card-body">

            <h3 class="mb-4 text-center">👤 Выбор игрока</h3>

            <c:if test="${not empty players}">
                <h5>Существующие игроки</h5>

                <c:forEach var="p" items="${players}">
                    <form method="post" action="${pageContext.request.contextPath}/login" class="mb-2">

                        <input type="hidden" name="login" value="${p.login}">

                        <select name="questId" class="form-select mb-2">
                            <c:forEach var="q" items="${quests}">
                                <option value="${q}">${q}</option>
                            </c:forEach>
                        </select>

                        <button class="btn btn-outline-primary w-100 player-btn">
                                ${p.login}
                            — 🎮 ${p.gamesPlayed}
                            | 🏆 ${p.wins}
                            | 💀 ${p.losses}
                        </button>
                    </form>
                </c:forEach>

                <hr/>
            </c:if>

            <h5>Новый игрок</h5>

            <form method="post" action="${pageContext.request.contextPath}/login">

                <input class="form-control mb-2 text-center"
                       name="login"
                       placeholder="Введите логин"
                       required>

                <select name="questId" class="form-select mb-2" required>
                    <c:forEach var="q" items="${quests}">
                        <option value="${q}">${q}</option>
                    </c:forEach>
                </select>

                <button class="btn btn-success w-100">
                    Создать и играть
                </button>
            </form>

            <c:if test="${not empty error}">
                <div class="alert alert-danger mt-3">
                        ${error}
                </div>
            </c:if>

        </div>
    </div>
</div>

</body>
</html>
