<%@ page isErrorPage="true" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<html>

<c:set var="title" value="Error" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%-- HEADER --%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%-- HEADER --%>

<div class="content">
    <h2 class="error">Page that you were requesting was not found!!!</h2>
</div>
</body>
</html>