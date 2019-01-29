<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="bar bar-tab">
    <c:set var="menuItem" value='<%=request.getParameter("menuItem")%>'/>
    <c:set var="recruitmentList" value='${menuItem == "recruitmentList" ? "active":"" }'/>
    <c:if test="${userInfo.userType.code == 3}">
        <a class="tab-item external ${recruitmentList}" href="/recruitment/list">
            <span class="icon icon-menu"></span>
            <span class="tab-label">任务列表</span>
        </a>
        <a class="tab-item external" href="#">
            <span class="icon icon-message"></span>
            <span class="tab-label">申请记录</span>
        </a>
        <a class="tab-item external" href="#">
            <span class="icon icon-me"></span>
            <span class="tab-label">我</span>
        </a>
    </c:if>
</nav>