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
    <h2 class="display-5 fw-bold text-body-emphasis">Все закончилось хорошо!</h2>
    <div class="col-lg-6 mx-auto">
        <p class="lead mb-4">
<%
                RepositoryService r = new RepositoryService(Repository.getRepository());
                String introStop = r.getIntroStop();
            %>
            <%=introStop%>
        </p>
    </div>
    <br/>
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <p class="lead mb-4">
                Игрок: ${name}
            </p>

            <br/>
            <br/>
        </div>
    </div>

</div>

<script>

</script>

</body>
</html>