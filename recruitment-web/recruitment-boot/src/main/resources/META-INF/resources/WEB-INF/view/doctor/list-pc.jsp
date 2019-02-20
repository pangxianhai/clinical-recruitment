<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <title>任务列表</title>
    <link href="/static/css/lib/sui.min.css" rel="stylesheet">
    <script type="text/javascript"
            src="/static/js/lib/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/lib/sui.min.js"></script>
</head>
<body>
<jsp:include page="../components/navbar-pc.jsp">
    <jsp:param value="user" name="menuTarget"/>
    <jsp:param value="doctorList" name="menuAction"/>
</jsp:include>
<div class="sui-container">
    <ul class="sui-breadcrumb">
        <li><a href="#">医生管理</a></li>
        <li class="active">医生列表</li>
    </ul>
    <form class="sui-form form-search">
        <input type="text" class="input-medium search-query">
        <button type="submit" class="sui-btn btn-primary">搜索</button>
    </form>
    <table class="sui-table table-primary">
        <thead>
        <tr>
            <th>医生姓名</th>
            <th>手机号</th>
            <th>性别</th>
            <th>地址</th>
            <th>执业机构</th>
            <th>执业类别</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="doctor-panel"></tbody>
    </table>
    <div class="pagination" style="text-align: right;margin-right: 20px">
    </div>
</div>
</body>
<script type="text/javascript" src="/static/js/util/ajax.js?_v=${version}"></script>
<script type='text/javascript' src='/static/js/doctor/list-pc.js?_v=${version}'
        charset="utf-8"></script>
</html>