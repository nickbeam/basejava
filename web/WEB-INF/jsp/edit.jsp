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
        <h3>Секции:</h3>
        <%--<c:forEach var="type" items="<%=SectionType.values()%>">--%>
            <%--<dl>--%>
                <%--<dt>${type.title}</dt>--%>
                <%--<dd><input type="text" name="${type.name()}" size=30 value="${resume.getSection(type)}"></dd>--%>
            <%--</dl>--%>
        <%--</c:forEach>--%>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<ru.javaops.webapp.model.SectionType, ru.javaops.webapp.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="ru.javaops.webapp.model.Section"/>
            <h3><a>${type.title}</a></h3>
            <c:choose>
                <c:when test="${type=='PERSONAL' || type=='OBJECTIVE'}">
                    <label>
                        <input type="text" name="${type}" size=60 value="<%=((TextSection) section).getText()%>">
                    </label>
                </c:when>
                <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                    <p><label>
                        <textarea name="${type}" rows="3" cols="80"><%=String.join("\n",((ListSection) section).getItems())%></textarea>
                    </label></p>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="organisation" items="<%=((OrganisationSection) section).getOrganisations()%>" varStatus="counter">
                        <h4><a>${organisation.name}</a></h4>
                        <c:forEach var="position" items="${organisation.positions}">
                            <%--<jsp:useBean id="position" type="ru.javaops.webapp.model.Organisation.Position"/>--%>
                            <label for="start">с:</label>
                            <input type="date" id="start" name="${position.startDate}${counter.index}"
                                   value="${position.startDate}"
                                   min="1900-01-01" max="<%=LocalDate.now()%>">
                            <label for="end">по:</label>
                            <input type="date" id="end" name="${position.endDate}${counter.index}"
                                   value="${position.endDate}"
                                   min="1900-01-01"><br>
                            <label>Должность: </label>
                            <input type="text" name="${position.head}${counter.index}head" size="60" value="${position.head}">
                            <%--<h5><label>${position.head}<br>--%>
                                <textarea name="${position.description}${counter.index}" cols="80" rows="3">${position.description}</textarea><br>
                            <%--</label></h5>--%>
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
