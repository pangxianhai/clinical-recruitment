<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${patientVOList}" var="patientInfo">
    <tr>
        <td>${patientInfo.userInfoVO.realName}</td>
        <td>${patientInfo.userInfoVO.phone}</td>
        <td>${patientInfo.userInfoVO.gender.desc}</td>
        <td>${patientInfo.userInfoVO.nickname}</td>
        <td>${patientInfo.address}</td>
        <td>${patientInfo.age}</td>
        <td>
            <c:if test="${patientInfo.userInfoVO.status.code == 1}">
                <span class="sui-text-success">${patientInfo.userInfoVO.status.desc}</span>
            </c:if>
            <c:if test="${patientInfo.userInfoVO.status.code == 2}">
                <span class="sui-text-danger">${patientInfo.userInfoVO.status.desc}</span>
            </c:if>
        </td>
        <td>
            <c:if test="${patientInfo.userInfoVO.status.code == 1}">
                <a href="javascript:void(0);" item="freeze" userId="${patientInfo.userInfoVO.userId}"
                   class="sui-btn btn-small btn-warning">冻结</a>
            </c:if>
            <c:if test="${patientInfo.userInfoVO.status.code == 2}">
                <a href="javascript:void(0);" item="unfreeze" userId="${patientInfo.userInfoVO.userId}"
                   class="sui-btn btn-small btn-primary">解冻</a>
            </c:if>
        </td>
    </tr>
</c:forEach>
<input type="hidden" id="totalPages" value="${paginator.totalPage}"/>