<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${recruitmentInfoList}" var="recruitmentInfo">
    <tr>
        <td>${recruitmentInfo.recruitmentId}</td>
        <td>${recruitmentInfo.title}</td>
        <td>${recruitmentInfo.registerCode}</td>
        <td>${recruitmentInfo.practiceStages}</td>
        <td>${recruitmentInfo.indication}</td>
        <td>${recruitmentInfo.drugName}</td>
        <td>${recruitmentInfo.drugType}</td>
        <td>${recruitmentInfo.recruitmentNumber}</td>
        <td>${recruitmentInfo.startTime}</td>
        <td>${recruitmentInfo.stopTime}</td>
        <td>${recruitmentInfo.status.desc}</td>
        <td>详情</td>
    </tr>
</c:forEach>
<input type="hidden" id="totalPages" value="${paginator.totalPage}"/>