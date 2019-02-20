<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .sui-container {
        margin: 60px 0 0 2%;
        width: 88%;
        float: left;
    }

    .nav-large {
        margin: 60px 0 0 0;
        width: 9%;
        float: left;
        border-right: 1px solid #28a3ef;
    }

    .sui-nav.nav-list > .active > a {
        color: #28a3ef;
        background-color: #FFF;
        text-shadow: 0 0 #FFF;
    }

    .sui-nav .nav-header {
        color: #bbb;
    }

    .sui-nav.nav-list > li > a {
        color: #222;
    }
</style>
<c:set var="menuTarget" value='<%=request.getParameter("menuTarget")%>'/>
<c:set var="recruitmentTarget" value='${menuTarget == "recruitment" ? "active":"" }'/>
<c:set var="userTarget" value='${menuTarget == "user" ? "active":"" }'/>
<div class="sui-navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <a href="/recruitment/list-pc" class="sui-brand">爱之募</a>
        <ul class="sui-nav">
            <li class="${recruitmentTarget}">
                <a href="/recruitment/list-pc">任务管理</a>
            </li>
            <li class="${userTarget}">
                <a href="/doctor/list-pc">用户管理</a>
            </li>
            <li>
                <a href="#">管理员管理</a>
            </li>
        </ul>
    </div>
</div>
<c:set var="menuAction" value='<%=request.getParameter("menuAction")%>'/>
<div class="nav-large">
    <ul class="sui-nav nav-list">
        <c:if test="${menuTarget == 'recruitment'}">
            <c:set var="recruitmentList" value='${menuAction == "recruitmentList" ? "active":"" }'/>
            <c:set var="recruitmentAdd" value='${menuAction == "recruitmentAdd" ? "active":"" }'/>
            <c:set var="recruitmentApplication"
                   value='${menuAction == "recruitmentApplication" ? "active":"" }'/>
            <li class="${recruitmentList}">
                <a href="/recruitment/list-pc">任务列表</a>
            </li>
            <li class="${recruitmentAdd}">
                <a href="/recruitment/add-pc">发布任务</a>
            </li>
            <li class="${recruitmentApplication}">
                <a href="/recruitmentapplication/list-pc">申请记录</a>
            </li>
        </c:if>
        <c:if test="${menuTarget == 'user'}">
            <c:set var="doctorList" value='${menuAction == "doctorList" ? "active":"" }'/>
            <c:set var="doctorAdd" value='${menuAction == "doctorAdd" ? "active":"" }'/>
            <li class="nav-header">医生</li>
            <li class="${doctorList}">
                <a href="/doctor/list-pc">医生列表</a>
            </li>
            <li class="${doctorAdd}">
                <a href="#">添加医生</a>
            </li>
            <li class="nav-header">患者</li>
            <li class="">
                <a href="#">患者列表</a>
            </li>
            <li class="">
                <a href="#">添加患者</a>
            </li>
        </c:if>
    </ul>
</div>