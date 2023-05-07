<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
    <c:set var="title" value="Analytics page" scope="page" />
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="page">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <div class="content">

            <div class="stat_table">
                <h2>Companies statistics</h2>
                <table>
                    <tr>
                        <th>Company</th>
                        <th>_id</th>
                        <th>info</th>
                    </tr>
                    <c:forEach var="companyStat" items="${requestScope.companiesStat}">
                        <tr>
                            <td>${companyStat.company}</td>
                            <td><pre>${companyStat._id.toString()}</pre></td>
                            <td>
                                <table>
                                    <tr>
                                        <th>stat name</th>
                                        <th>value</th>
                                    </tr>
                                    <tr>
                                        <td>${companyStat.statName}</td>
                                        <td>${companyStat.statValue}</td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
<br/>
            <div class="stat_table">
                <h2>Additional statistics</h2>
                <table>
                    <tr>
                        <th>operation</th>
                        <th>result ( time in ms )</th>
                    </tr>
                    <c:forEach var="stat" items="${requestScope.additionalStat}">
                    <tr>
                        <td>${stat.operationName}</td>
                        <td>${stat.operationValue}</td>
                    </tr>
                    </c:forEach>
                </table>
            </div>


    </div>
</div>
</body>
</html>