<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Text Quest — Пролог</title>

    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
</head>
<body class="bg-dark text-light">

<div class="container mt-5">
    <div class="card bg-secondary text-light shadow">
        <div class="card-body text-center">

            <h2 class="card-title mb-4">🛸 Потерянный сигнал</h2>

            <p class="card-text">
                Ты очнулся на борту неизвестного корабля.<br/>
                Последнее, что ты помнишь — вспышку света и тревожный сигнал.<br/>
                Кто ты? Где ты? И стоит ли доверять тем, кто тебя нашёл?
            </p>

            <form method="get" action="restart" class="mt-4">
                <button type="submit" class="btn btn-warning btn-lg">
                    Начать игру
                </button>
            </form>

        </div>
    </div>
</div>

</body>
</html>
