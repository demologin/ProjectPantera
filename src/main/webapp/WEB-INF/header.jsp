<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <nav class="navbar navbar-expand-lg fixed-top bg-dark navbar-dark">
        <div class="container"><a class="navbar-brand" href="${pageContext.request.contextPath}/"><strong>QUESTS</strong></a><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-1" type="button"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse" id="navcol-1">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/">Quests</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Register</a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
</body>
</html>
