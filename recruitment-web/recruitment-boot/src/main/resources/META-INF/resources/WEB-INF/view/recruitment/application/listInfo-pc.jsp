<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${applicationVOList}" var="application">
    <tr>
        <td>${application.applicationId}</td>
        <td>${application.recruitmentVO.title}</td>
        <td>${application.recruitmentVO.registerCode}</td>
        <td>${application.patientVO.userInfoVO.realName}</td>
        <td>${application.doctorInfoVO.userInfoVO.realName}</td>
        <td>${application.status.desc}</td>
        <td>${application.applicationTime}</td>
        <td>详情</td>
    </tr>
</c:forEach>
<input type="hidden" id="totalPages" value="${paginator.totalPage}"/>