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
        <td>
            <a class="sui-btn btn-small btn-primary"
               href="/recruitment/detailPc/${recruitmentInfo.recruitmentId}">
                详情
            </a>
            <a class="sui-btn btn-small btn-success"
               href="/recruitment/updatePc/${recruitmentInfo.recruitmentId}">
                更新
            </a>
            <c:if test="${recruitmentInfo.status.code == 0}">
                <a class="sui-btn btn-small btn-info" href="javascript:void(0)">
                    开始
                </a>
            </c:if>
            <c:if test="${recruitmentInfo.status.code == 1}">
                <a class="sui-btn btn-small btn-danger" href="javascript:void(0)">
                    结束
                </a>
            </c:if>
            <c:if test="${recruitmentInfo.status.code == 2}">
                <a class="sui-btn btn-small btn-warning" href="javascript:void(0)">
                    重新开始
                </a>
            </c:if>
        </td>
    </tr>
</c:forEach>
<input type="hidden" id="totalPages" value="${paginator.totalPage}"/>