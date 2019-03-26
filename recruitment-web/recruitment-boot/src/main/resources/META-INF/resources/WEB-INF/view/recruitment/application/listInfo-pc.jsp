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
        <td>
            <a class="sui-btn btn-small btn-primary"
               href="/recruitmentapplication/detailPc/${application.applicationId}">
                详情
            </a>
            <c:if test="${application.status.code == 1}">
                <a class="sui-btn btn-small btn-success" applicationId="${application.applicationId}"
                   href="javascript:void(0)" item="accede">
                    入组
                </a>
            </c:if>
            <c:if test="${application.status.code == 2}">
                <a class="sui-btn btn-small btn-danger" applicationId="${application.applicationId}"
                   href="javascript:void(0)" item="cancelAccede">
                    取消入组
                </a>
            </c:if>
        </td>
    </tr>
</c:forEach>
<input type="hidden" id="totalPages" value="${paginator.totalPage}"/>