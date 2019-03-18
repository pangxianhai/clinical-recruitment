<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${doctorInfoVOList}" var="doctorInfo">
    <tr>
        <td>${doctorInfo.userInfoVO.realName}</td>
        <td>${doctorInfo.userInfoVO.phone}</td>
        <td>${doctorInfo.userInfoVO.nickname}</td>
        <td>${doctorInfo.userInfoVO.gender.desc}</td>
        <td>${doctorInfo.address}</td>
        <td>${doctorInfo.medicalInstitution}</td>
        <td>${doctorInfo.medicalCategory}</td>
        <td>
            <c:if test="${doctorInfo.userInfoVO.status.code == 1}">
                <span class="sui-text-success">${doctorInfo.userInfoVO.status.desc}</span>
            </c:if>
            <c:if test="${doctorInfo.userInfoVO.status.code == 2}">
                <span class="sui-text-danger">${doctorInfo.userInfoVO.status.desc}</span>
            </c:if>
        </td>
        <td>
            <c:if test="${doctorInfo.userInfoVO.status.code == 1}">
                <a href="javascript:void(0);" item="freeze" userId="${doctorInfo.userInfoVO.userId}"
                   class="sui-btn btn-small btn-warning">冻结</a>
            </c:if>
            <c:if test="${doctorInfo.userInfoVO.status.code == 2}">
                <a href="javascript:void(0);" item="unfreeze" userId="${doctorInfo.userInfoVO.userId}"
                   class="sui-btn btn-small btn-primary">解冻</a>
            </c:if>
        </td>
    </tr>
</c:forEach>
<input type="hidden" id="totalPages" value="${paginator.totalPage}"/>