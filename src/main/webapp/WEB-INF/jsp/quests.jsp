<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h2>Доступные квесты</h2>

<ul class="quest-list">
    <c:forEach var="q" items="${requestScope.quests}">
        <li>
            <a href="start?id=${q.id}">
                    ${q.title}
            </a>
        </li>
    </c:forEach>
</ul>