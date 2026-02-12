<%--
  Created by IntelliJ IDEA.
  User: Kuzmin Mykhaylo
--%>
<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8" language="java" %>
<%@ page import="com.javarush.khmelov.entity.Story" %>
<%@ page import="com.javarush.khmelov.entity.Choice" %>
<%@ page import="com.javarush.khmelov.entity.EndingType" %>
<%@ page import="com.javarush.khmelov.entity.StoryNode" %>
<%@ page import="com.javarush.khmelov.entity.GameSession" %>
<%
    Story story = (Story) request.getAttribute("story");
    StoryNode node = (StoryNode) request.getAttribute("node");
    GameSession stats = (GameSession) request.getAttribute("stats");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title><%= story.getTitle() %></title>
    <style>
        body {
            ont-family: Arial, sans-serif;
            max-width: 720px;
            margin: 40px auto;
            background-color: #ccc;
        }
        .story-note > * {
            text-align: center;
        }
        .card {
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 20px;
            background-color: #fff;
        }
        .choices {
            margin-top: 16px;
            display: grid;
            gap: 10px;
        }
        a.btn {
            display: block;
            padding: 12px 14px;
            border-radius: 8px;
            text-decoration: none;
            border: 1px solid #222;
        }
        .end {
            text-align: center;
            margin-top: 18px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="card">
    <div class="story-note">
        <h2><%= story.getTitle() %></h2>
        <p><%= node.getText() %></p>
    </div>

    <div class="choices">
        <% for (Choice c : node.getChoices()) { %>
        <a class="btn" href="<%= request.getContextPath() %>/home?story=<%= story.getCode() %>&node=<%= c.getNextNodeKey() %>">
            <%= c.getLabel() %>
        </a>
        <% } %>
    </div>

    <% if (node.isEnding()) { %>
    <div class="end">
        <% if (node.getEndingType() == EndingType.WIN) { %>
        <b style="color: darkgreen">Победа</b>
        <% } else { %>
        <b style="color: darkred">Поражение</b>
        <% } %>
    </div>
    <div style="margin-top:10px;">
        <a class="btn" href="<%= request.getContextPath() %>/home?story=<%= story.getCode() %>&node=START">Начать заново</a>
    </div>
    <% } %>
</div>

<div class="card" style="margin-top: 20px;">
    <b>Статистика сессии:</b>
    <span style="color: blueviolet">Игр: <%= stats.getGamesPlayed() %>,</span>
    <span style="color: darkgreen">Побед: <%= stats.getWins() %>,</span>
    <span style="color: darkred">Поражений: <%= stats.getLosses() %></span>
</div>
</body>
</html>
