<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Text Quest</title>

    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow">
        <div class="card-body">

            <h4 class="card-title mb-4">
                ${scene.text}
            </h4>

            <c:choose>

                <c:when test="${not gameOver}">
                    <form method="get" action="game">
                        <c:forEach var="choice" items="${scene.choices}">
                            <button
                                    type="submit"
                                    name="choice"
                                    value="${choice.id}"
                                    class="btn btn-primary w-100 mb-2">
                                    ${choice.text}
                            </button>
                        </c:forEach>
                    </form>
                </c:when>

                <c:otherwise>
                    <c:choose>
                        <c:when test="${win}">
                            <div class="alert alert-success text-center">
                                <h4>🎉 Победа!</h4>
                                <p>Ты успешно завершил квест.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-danger text-center">
                                <h4>💀 Поражение</h4>
                                <p>Попробуй ещё раз.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <form method="get" action="restart" class="text-center mt-3">
                        <button type="submit" class="btn btn-outline-secondary">
                            Начать заново
                        </button>
                    </form>
                </c:otherwise>

            </c:choose>

        </div>
    </div>
</div>

</body>
</html>
