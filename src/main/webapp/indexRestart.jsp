<%@ page import="services.RepositoryService" %>
<%@ page import="repository.Repository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
       <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
          integrity="sha384-SgOJa3DmI69IUzQ2PVdRZhwQ+dy64/BUtbMJw1MZ8t5HZApcHrRKUc4W0kG879m7" crossorigin="anonymous">
    <link href="/css/MyCSS.css" rel="stylesheet">
    <title>Приключения мотоциклиста</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <script src=https://code.jquery.com/jquery-3.6.0.min.js></script>
</head>

<body>
<hider>
 <div class="collapse bg-dark" id="navbarHeader">
    <div class="container">
      <div class="row">
        <div class="col-sm-8 col-md-7 py-4">
          <h4 class="text-white">Описание</h4>
          <p class="text-muted">Добавлю здесь описание пейзажей во время поездки.</p>
        </div>
        <div class="col-sm-4 offset-md-1 py-4">
          <h4 class="text-white">Контакты</h4>
          <ul class="list-unstyled">
            <li><a href="#" class="text-white">Телефон</a></li>
            <li><a href="#" class="text-white">Telegramm</a></li>
            <li><a href="#" class="text-white">Email me</a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <div class="navbar navbar-dark bg-dark shadow-sm">
    <div class="container">
      <a href="#" class="navbar-brand d-flex align-items-center">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" aria-hidden="true" class="me-2" viewBox="0 0 24 24"><path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"/><circle cx="12" cy="13" r="4"/></svg>
        <strong>Альбом мотоциклиста</strong>
      </a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
    </div>
  </div>
<header>

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
            <p class="lead mb-4">
                Игрок: ${name}
            </p>
            <br/>
            <br/>
        </div>
    </div>
    <div class="text-end">
        <button type="button" onclick="start()" class="btn btn-outline-primary me-2">Еще попытка...</button>
    </div>
     <div class="text-end">
            <button type="button" onclick="stop()" class="btn btn-outline-primary me-2">Выйти из игры...</button>
        </div>
</div>

<script>
    function start() {
        const url = "greeting";
        $.ajax({
            method: "GET",
            url: url,
            dataType: "html",
            success: function () {
                document.location.href = "greeting";
            }
        });
    }
    function stop() {
            const url = "stop";
            $.ajax({
                method: "GET",
                url: url,
                dataType: "html",
                success: function () {
                    document.location.href = "stop";
                }
            });
        }

</script>

</body>
</html>