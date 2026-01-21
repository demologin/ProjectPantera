<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/style.css'/>">
<html>
<head>
    <title>Play Game</title>
</head>
<body>
<h1>${requestScope.quest.title}</h1>
<form class="form-horizontal">
    <fieldset>

        <!-- Form Name -->
        <legend>Вопрос</legend>

        <!-- Multiple Radios -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="radios">${requestScope.question.text}</label>
            <div class="col-md-4">
                <c:forEach var="answer" items="${requestScope.answers}">
                    <div class="radio" id="radios">
                        <label for="radios-${answer.id}">
                            <input type="radio" name="radios" id="radios-${answer.id}" value="${answer.text}"
                                   checked="checked">
                                ${answer.text}
                        </label>
                    </div>
                </c:forEach>
            </div>
        </div>

    </fieldset>
</form>
</body>
</html>
