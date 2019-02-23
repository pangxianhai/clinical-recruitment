<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${userInfoVOList}" var="manageInfo">
    <tr>
        <td>${manageInfo.realName}</td>
        <td>${manageInfo.phone}</td>
        <td>${manageInfo.gender.desc}</td>
        <td>${manageInfo.status.desc}</td>
        <td>详情</td>
    </tr>
</c:forEach>
<input type="hidden" id="totalPages" value="${paginator.totalPage}"/>