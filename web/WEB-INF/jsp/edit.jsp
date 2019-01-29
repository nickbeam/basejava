<%--
  Created by IntelliJ IDEA.
  User: nick
  Date: 19.12.2018
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="ru.javaops.webapp.model.*" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="ru.javaops.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="shortcut icon" href="img/favicon.gif">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javaops.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<ru.javaops.webapp.model.SectionType, ru.javaops.webapp.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="ru.javaops.webapp.model.Section"/>
            <h3><a>${type.title}</a></h3>
            <c:choose>
                <c:when test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                    <input type="text" name="${type}" size=56 value="<%=((TextSection) section).getText()%>">
                </c:when>
                <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                    <textarea name="${type}" rows="3" cols="58"><%=String.join("\n",((ListSection) section).getItems())%></textarea>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="organisation" items="<%=((OrganisationSection) section).getOrganisations()%>" varStatus="counter">
                        <dl>
                            <dt>Название организации:</dt>
                            <dd><input type="text" name="${type}" size=52 value="${organisation.name}"></dd>
                        </dl>

                        <dl>
                            <dt>Сайт организации:</dt>
                            <dd><input type="text" name="${type}url" size=52 value="${organisation.url}"></dd>
                        </dl>

                        <c:forEach var="position" items="${organisation.positions}">
                            <jsp:useBean id="position" type="ru.javaops.webapp.model.Organisation.Position"/>
                            <dl>
                                <dt>с:</dt>
                                <dd><input type="date" id="start" name="${type}${counter.index}startDate"
                                           value="${position.startDate}"
                                           min="1900-01-01" max="<%=LocalDate.now()%>"></dd>
                            </dl>

                            <dl>
                                <dt>по:</dt>
                                <dd><input type="date" id="end" name="${type}${counter.index}endDate"
                                           value="${position.endDate}"
                                           min="1900-01-01"></dd>
                            </dl>

                            <dl>
                                <dt>Должность:</dt>
                                <dd><input type="text" name="${type}${counter.index}head" size="52" value="${position.head}"></dd>
                            </dl>

                            <dl>
                                <dt>Описание:</dt>
                                <dd><textarea name="${type}${counter.index}description" cols="54" rows="4">${position.description}</textarea><br></dd>
                            </dl>
                        </c:forEach>
                    </c:forEach>
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
