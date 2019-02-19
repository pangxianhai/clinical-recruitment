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
    }

    .sui-nav .nav-header {
        color: #bbb;
    }

    .sui-nav.nav-list > li > a {
        color: #222;
    }
</style>
<div class="sui-navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <a href="/recruitment/list-pc" class="sui-brand">爱之募</a>
        <ul class="sui-nav">
            <li class="active">
                <a href="/recruitment/list-pc">任务管理</a>
            </li>
            <li>
                <a href="#">医生管理</a>
            </li>
            <li>
                <a href="#">患者管理</a>
            </li>
            <li>
                <a href="#">管理员管理</a>
            </li>
        </ul>
    </div>
</div>
<div class="nav-large">
    <ul class="sui-nav nav-list">
        <li class="nav-header">服装</li>
        <li class="active"><a>阿迪达斯</a></li>
        <li><a>耐克</a></li>
        <li><a>匡威</a></li>
        <li class="nav-header">美食</li>
        <li><a>湘菜 </a></li>
        <li><a>自助</a></li>
        <li><a>日料</a></li>
    </ul>
</div>