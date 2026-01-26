<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-SgOJa3DmI69IUzQ2PVdRZhwQ+dy64/BUtbMJw1MZ8t5HZApcHrRKUc4W0kG879m7" crossorigin="anonymous">
    <link href="/css/MyCSS.css" rel="stylesheet">
    <title>Приключения мотоциклиста</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <script src=https://code.jquery.com/jquery-3.6.0.min.js></script>
</head>

<body>
<div class="px-4 py-5 my-5 text-center">
    <div class="col-lg-6 mx-auto">
        <p class="lead mb-4">
            ${question}
        </p>
    </div>

    <div class="col-lg-6 col-xxl-4 my-5 mx-auto">
        <div class="d-grid gap-2">
            <button class="btn btn-primary" onclick="launch('${idAnswer1}')" type="button"
                    value=${idAnswer1}>${answer1}</button>
            <button class="btn btn-primary" onclick="launch('${idAnswer2}')" type="button"
                    value=${idAnswer2}>${answer2}</button>
        </div>
    </div>

    <br/>
    <br/>

    <p class="lead">
        Статистика:
        <br/>
        Имя в игре: ${name}
        <br/>
        Количество сыгранных игр: ${count}
    </p>
</div>
</body>

<script>
    function launch(id) {
        document.location.href = "answers?n=" + id;
    }

</script>
</html>