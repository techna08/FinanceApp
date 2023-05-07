<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
    <c:set var="title" value="Logs page" scope="page" />
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
    <div class="page">
        <%@ include file="/WEB-INF/jspf/header.jspf" %>
            <br/><br/><br/><br/><br/><br/><br/><br/><br/>
            <div class="logs">
                <table class="logs_table">
                    <tr>
                        <th class="log">Log</th>
                        <th>Date</th>
                        <th>Timestamp</th>
                    </tr>
                    <c:forEach var="log" items="${requestScope.logs}">

                        <tr>
                            <td class="log"><p>${log.log}</p></td>
                            <td>${log._id.date}</td>
                            <td>${log._id.timestamp}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

    </div>
</body>
</html>
