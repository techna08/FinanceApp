<%@ page isErrorPage="true" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Error" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%-- HEADER --%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%-- HEADER --%>

<div class="content">
    <%-- CONTENT --%>

    <h2 class="error">Ooops... Internal server error</h2>

    <%-- this way we get the error information (error 500)--%>
    <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
    <c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>
    <%-- CONTENT --%>
</div>
</body>
</html>

