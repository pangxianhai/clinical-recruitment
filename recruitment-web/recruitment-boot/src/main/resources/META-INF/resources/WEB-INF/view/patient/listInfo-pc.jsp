<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${patientVOList}" var="patientInfo">
    <tr>
        <td>${patientInfo.userInfoVO.realName}</td>
        <td>${patientInfo.userInfoVO.phone}</td>
        <td>${patientInfo.userInfoVO.gender.desc}</td>
        <td>${patientInfo.address}</td>
        <td>${patientInfo.age}</td>
        <td>${patientInfo.userInfoVO.status.desc}</td>
        <td>详情</td>
    </tr>
</c:forEach>
<input type="hidden" id="totalPages" value="${paginator.totalPage}"/>