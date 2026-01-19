<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.javarush.golikov.quest.model.*" %>

<%
    QuestNode node = (QuestNode) request.getAttribute("node");
%>

<html>
<head>
    <!--suppress HtmlDeprecatedTag -->
    <title>Квест</title>
</head>
<body>

<h2><%= node.text() %></h2>

<!-- Кнопки ответов -->
<% for (Choice c : node.choices()) { %>
<form method="post" action="play">
    <input type="hidden" name="next" value="<%= c.next() %>">
    <button><%= c.text() %></button>
</form>
<% } %>

<hr>

<!-- Просто вернуться к списку квестов -->
<form action="quests" method="get">
    <button>К списку квестов</button>
</form>

<!-- Завершить квест как поражение -->
<form action="exitQuest" method="get">
    <button style="color:red">Выйти из квеста</button>
</form>

</body>
</html>
