<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <title>Предыстория</title>
  <style>
    body {
      font-family: 'Courier New', monospace;
      background-color: #000;
      color: #ccc;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
    }
    .prologue-container {
      max-width: 700px;
      padding: 50px;
      border-top: 2px solid #e60000;
      border-bottom: 2px solid #e60000;
      text-align: center;
      background: linear-gradient(180deg, #111 0%, #000 100%);
    }
    h1 {
      color: #e60000;
      text-transform: uppercase;
      letter-spacing: 3px;
      margin-bottom: 30px;
      font-size: 24px;
    }
    .story-text {
      font-size: 18px;
      line-height: 1.8;
      text-align: justify;
      margin-bottom: 40px;
      white-space: pre-wrap; /* Чтобы работали переносы строк \n из Java */
    }
    .btn-start {
      display: inline-block;
      padding: 15px 40px;
      border: 1px solid #e60000;
      color: #e60000;
      text-decoration: none;
      font-weight: bold;
      transition: 0.3s;
      text-transform: uppercase;
    }
    .btn-start:hover {
      background-color: #e60000;
      color: black;
      box-shadow: 0 0 15px #e60000;
    }
    .fade-in {
      animation: fadeIn 2s;
    }
    @keyframes fadeIn {
      from { opacity: 0; }
      to { opacity: 1; }
    }
  </style>
</head>
<body>
<div class="prologue-container fade-in">
  <h1>Дело: ${sessionScope.questName}</h1>

  <div class="story-text">
    ${sessionScope.questPrologue}
  </div>

  <%-- Ссылка ведет на GameServlet, который откроет шаг 1 --%>
  <a href="game" class="btn-start">Приступить к задаче</a>
</div>
</body>
</html>