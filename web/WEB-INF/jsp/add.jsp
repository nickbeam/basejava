<%--
  Created by IntelliJ IDEA.
  User: nick
  Date: 19.12.2018
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="ru.javaops.webapp.model.ContactType" %>
<%@ page import="ru.javaops.webapp.model.SectionType" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="shortcut icon" href="img/favicon.gif">
    <link rel="stylesheet" href="css/style.css">
    <title>Новое резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${""}">
        <c:set var="counter" value="${1}"/>
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30"></dd>
            </dl>
        </c:forEach>

        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${type=='PERSONAL'}">
                    <h3>Личные качества:</h3>
                    <dl>
                        <input type="text" name="${type}" size=56>
                    </dl>
                </c:when>
                <c:when test="${type=='OBJECTIVE'}">
                    <h3>Позиция:</h3>
                    <dl>
                        <input type="text" name="${type}" size=56>
                    </dl>
                </c:when>
                <c:when test="${type=='ACHIEVEMENT'}">
                    <h3>Достижения:</h3>
                    <dl>
                        <textarea name="${type}" rows="3" cols="58"></textarea>
                    </dl>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS'}">
                    <h3>Квалификация:</h3>
                    <dl>
                        <textarea name="${type}" rows="3" cols="58"></textarea>
                    </dl>
                </c:when>
                <c:when test="${type=='EXPERIENCE'}">
                    <h3>Опыт работы</h3>
                    <dl>
                        <dt>Название организации:</dt>
                        <dd><input type="text" name="${type}" size=52></dd>
                    </dl>

                    <dl>
                        <dt>Сайт организации:</dt>
                        <dd><input type="text" name="${type}url" size=52></dd>
                    </dl>

                    <%--<jsp:useBean id="position" type="ru.javaops.webapp.model.Organisation.Position"/>--%>
                    <dl>
                        <dt>с:</dt>
                        <dd><input type="date" name="${type}${counter}startDate"
                                   min="1900-01-01" max="<%=LocalDate.now()%>"></dd>
                    </dl>

                    <dl>
                        <dt>по:</dt>
                        <dd><input type="date" name="${type}${counter}endDate"
                                   min="1900-01-01"></dd>
                    </dl>

                    <dl>
                        <dt>Должность:</dt>
                        <dd><input type="text" name="${type}${counter}head" size="52"></dd>
                    </dl>

                    <dl>
                        <dt>Описание:</dt>
                        <dd><textarea name="${type}${counter}description" cols="54" rows="4"></textarea><br></dd>
                    </dl>
                </c:when>
                <c:when test="${type=='EDUCATION'}">
                    <h3>Образование</h3>
                    <dl>
                        <dt>Название организации:</dt>
                        <dd><input type="text" name="${type}" size=52></dd>
                    </dl>

                    <dl>
                        <dt>Сайт организации:</dt>
                        <dd><input type="text" name="${type}url" size=52></dd>
                    </dl>

                    <%--<jsp:useBean id="position" type="ru.javaops.webapp.model.Organisation.Position"/>--%>
                    <dl>
                        <dt>с:</dt>
                        <dd><input type="date" name="${type}${counter}startDate"
                                   min="1900-01-01" max="<%=LocalDate.now()%>"></dd>
                    </dl>

                    <dl>
                        <dt>по:</dt>
                        <dd><input type="date" name="${type}${counter}endDate"
                                   min="1900-01-01"></dd>
                    </dl>

                    <dl>
                        <dt>Должность:</dt>
                        <dd><input type="text" name="${type}${counter}head" size="52"></dd>
                    </dl>

                    <dl>
                        <dt>Описание:</dt>
                        <dd><textarea name="${type}${counter}description" cols="54" rows="4"></textarea><br></dd>
                    </dl>
                </c:when>
            </c:choose>
        </c:forEach>

        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
