<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!-- Общая шапка сайта для всех страниц -->
<header>
  <nav>
    <div class="logo">
      <h1>Quest Pantera</h1>
    </div>
    <ul class="nav-links">
      <li><a href="${pageContext.request.contextPath}/home-page">Главная</a></li>
      <c:if test="${not empty sessionScope.username}">
        <li><a href="${pageContext.request.contextPath}/quest-dragon">Квест</a></li>
        <li><a href="${pageContext.request.contextPath}/statistic-page">Статистика</a></li>
        <li><a href="${pageContext.request.contextPath}/logout">Выход (${sessionScope.username})</a></li>
      </c:if>
      <c:if test="${empty sessionScope.username}">
        <li><a href="${pageContext.request.contextPath}/login-page">Вход</a></li>
        <li><a href="${pageContext.request.contextPath}/register-page">Регистрация</a></li>
      </c:if>
    </ul>
  </nav>
</header>

<style>
  header {
    background-color: #2c3e50;
    color: white;
    padding: 1rem 2rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .logo h1 {
    margin: 0;
    font-size: 1.5rem;
  }

  .nav-links {
    list-style: none;
    display: flex;
    gap: 1.5rem;
    margin: 0;
    padding: 0;
  }

  .nav-links a {
    color: white;
    text-decoration: none;
    transition: color 0.3s;
  }

  .nav-links a:hover {
    color: #3498db;
  }
</style>