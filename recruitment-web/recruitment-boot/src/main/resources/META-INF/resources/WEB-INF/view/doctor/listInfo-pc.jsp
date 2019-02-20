<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${doctorInfoVOList}" var="doctorInfo">
    <tr>
        <td>${doctorInfo.userInfoVO.realName}</td>
        <td>${doctorInfo.userInfoVO.phone}</td>
        <td>${doctorInfo.userInfoVO.gender.desc}</td>
        <td>${doctorInfo.address}</td>
        <td>${doctorInfo.medicalInstitution}</td>
        <td>${doctorInfo.medicalCategory}</td>
        <td>${doctorInfo.userInfoVO.status.desc}</td>
        <td>详情</td>
    </tr>
</c:forEach>
<input type="hidden" id="totalPages" value="${paginator.totalPage}"/>