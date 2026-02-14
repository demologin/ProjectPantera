<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
 <head>
     <meta charset="utf-8">
     <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>



     <title>Приключения мотоциклиста</title>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <script src=https://code.jquery.com/jquery-3.6.0.min.js></script>

<!--<style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }

      .b-example-divider {
        height: 3rem;
        background-color: rgba(0, 0, 0, .1);
        border: solid rgba(0, 0, 0, .15);
        border-width: 1px 0;
        box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
      }

      .b-example-vr {
        flex-shrink: 0;
        width: 1.5rem;
        height: 100vh;
      }

      .bi {
        vertical-align: -.125em;
        fill: currentColor;
      }

      .nav-scroller {
        position: relative;
        z-index: 2;
        height: 2.75rem;
        overflow-y: hidden;
      }

      .nav-scroller .nav {
        display: flex;
        flex-wrap: nowrap;
        padding-bottom: 1rem;
        margin-top: -1px;
        overflow-x: auto;
        text-align: center;
        white-space: nowrap;
        -webkit-overflow-scrolling: touch;
      }
    </style>



     Custom styles for this template -->

 </head>

   <!-- <header>
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
        <button type="button" onclick="stop()"  class="btn btn-primary btn-lg">Закончить игру</button>
      </div>
    </div>
  </header>-->


<link href="/css/signin.css" rel="stylesheet">

  <body class="text-center">


<main class="form-signin w-100 m-auto">

  <form>

    <img class="mb-4" src="../assets/logo.svg" alt="" width="72" height="57">
    <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

<form action="greeting" method="post"">
  <% if (request.getAttribute("error") != null && request.getAttribute("error") != "0") { %>
                    <div class="alert alert-danger" role="alert">
                        Invalid username or password!
                    </div>
                <% } %>

    <div class="form-floating">
      <input type="search"  class="form-control form-control-light text-bg-light"  id="idName"  name="yourname"  value="<%= request.getAttribute("name") %>" /><br/>
      <label for="floatingInput">Username</label>
    </div>
    <div class="form-floating">
      <input type="password"  class="form-control" id="floatingPassword1" placeholder="Password" name="password"  value="<%= request.getAttribute("password") %>" /><br/>
      <label for="floatingPassword">Password 4 digits</label>
    </div>

    <div class="checkbox mb-3">
      <label>
        <input type="checkbox"   value="remember"  name="someChek"  <% if ( request.getAttribute ("chekValue") == "remember") { out.print( "checked=\"checked\"") ;  } %> >Remember me<br>



      </label>

    </div>

     <button type="button"  onclick="start()" class="btn btn-primary btn-lg">Sign in</button>
    <p class="mt-5 mb-3 text-muted">&copy; 2017–2022</p>

  </form>

</main>


<script>

         function start() {
                       const url = "greeting";

                                   const name = document.getElementById('idName').value;
                                   const password = document.getElementById('floatingPassword1').value;
                                  const someChek  = document.querySelector('input[type="checkbox"]:checked')?.value;
                                  const fullname=name+";"+someChek+"/"+password+"e";




                                                    $.ajax({
                                                                    method: "POST",
                                                                    url: url,
                                                                    dataType: "html",
                                                                    data: {yourname: fullname},
                                                                    success: function () {
                                                                                               document.location.href = "greeting";

                                                                                           }
                                                                });


                   }



    </script>

  </body>
</html>
