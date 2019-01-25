<%--
  Created by IntelliJ IDEA.
  User: nick
  Date: 19.12.2018
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="ru.javaops.webapp.model.SectionType" %>
<%@ page import="ru.javaops.webapp.model.ListSection" %>
<%@ page import="ru.javaops.webapp.model.TextSection" %>
<%@ page import="ru.javaops.webapp.model.OrganisationSection" %>
<%@ page import="ru.javaops.webapp.util.HtmlUtil" %>

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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javaops.webapp.model.ContactType, java.lang.String>"/>
            <tr>
                <td colspan="2">
                    <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
                </td>
            </tr>
        </c:forEach>

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javaops.webapp.model.SectionType, ru.javaops.webapp.model.Section>"/>
                <c:set var="type" value="${sectionEntry.key}"/>
                <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="ru.javaops.webapp.model.Section"/>
            <tr>
                <td colspan="2"><h2><a name="type.name">${type.title}</a></h2></td>
            </tr>
            <c:choose>
            <c:when test="${type=='OBJECTIVE'}">
            <tr>
                <td colspan="2">
                    <h3><%=((TextSection) section).getText()%></h3>
                </td>
            </tr>
            </c:when>
            <c:when test="${type=='PERSONAL'}">
            <tr>
                <td colspan="2">
                    <%=((TextSection) section).getText()%>
                </td>
            </tr>
            </c:when>
            <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
            <tr>
                <td colspan="2">
                    <ul>
                        <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                            <li>${item}</li>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
            </c:when>
            <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
            <c:forEach var="org" items="<%=((OrganisationSection) section).getOrganisations()%>">
            <tr>
                <td colspan="2">
                    <c:choose>
                        <c:when test="${empty org.url}">
                            <h3>${org.url}</h3>
                        </c:when>
                        <c:otherwise>
                            <h3><a href="${org.url}">${org.name}</a></h3>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <c:forEach var="position" items="${org.positions}">
                <jsp:useBean id="position" type="ru.javaops.webapp.model.Organisation.Position"/>
            <tr>
                <td width="15%" style="vertical-align: top"><%=HtmlUtil.formatDates(position)%>
                </td>
                <td><b>${position.head}</b><br>${position.description}</td>
            </tr>
            </c:forEach>
            </c:forEach>
            </c:when>
            </c:choose>

        </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
