<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${userInfoVOList}" var="manageInfo">
    <tr>
        <td>${manageInfo.realName}</td>
        <td>${manageInfo.phone}</td>
        <td>${manageInfo.gender.desc}</td>
        <td>
            <c:if test="${manageInfo.status.code == 1}">
                <span class="sui-text-success">${manageInfo.status.desc}</span>
            </c:if>
            <c:if test="${manageInfo.status.code == 2}">
                <span class="sui-text-danger">${manageInfo.status.desc}</span>
            </c:if>
        </td>
        <td>
            <c:if test="${manageInfo.status.code == 1}">
                <a href="javascript:void(0);" item="freeze" userId="${manageInfo.userId}"
                   class="sui-btn btn-small btn-warning">冻结</a>
            </c:if>
            <c:if test="${manageInfo.status.code == 2}">
                <a href="javascript:void(0);" item="unfreeze" userId="${manageInfo.userId}"
                   class="sui-btn btn-small btn-primary">解冻</a>
            </c:if>
        </td>
    </tr>
</c:forEach>
<input type="hidden" id="totalPages" value="${paginator.totalPage}"/>