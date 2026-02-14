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



 </head>




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
    <p class="mt-5 mb-3 text-muted">&copy; 2025–2026</p>

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
