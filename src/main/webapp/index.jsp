<%@ page import="services.RepositoryService" %>
<%@ page import="repository.Repository" %>
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
    <h2 class="display-5 fw-bold text-body-emphasis">Пролог</h2>
    <div class="col-lg-6 mx-auto">
        <p class="lead mb-4">
            <%
                RepositoryService r = new RepositoryService(Repository.getRepository());
                String intro = r.getIntro();
            %>
            <%=intro%>
        </p>
    </div>
    <br/>
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3" role="search">
                <input type="search" class="form-control form-control-light text-bg-light" id="idName" name="yourname"
                       placeholder="Введите имя..."
                       aria-label="Search">
            </form>
            <div class="text-end">
                <button type="button" onclick="start()" class="btn btn-outline-primary me-2">Поехали!</button>
            </div>
        </div>
    </div>
</div>

<script>
    function start() {
        const url = "greeting";
        const name = document.getElementById('idName').value;
        $.ajax({
            method: "POST",
            url: url,
            dataType: "html",
            data: {yourname: name},
            success: function () {
                document.location.href = "greeting";
            }
        });
    }
</script>

</body>
</html>