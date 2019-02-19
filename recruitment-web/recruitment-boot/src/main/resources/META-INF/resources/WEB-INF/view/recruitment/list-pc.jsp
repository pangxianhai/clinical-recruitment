<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <title>招募信息</title>
    <link href="/static/css/lib/sui.min.css" rel="stylesheet">
    <script type="text/javascript"
            src="/static/js/lib/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/lib/sui.min.js"></script>
</head>
<body>
<jsp:include page="../components/navbar-pc.jsp">
    <jsp:param value="recruitmentList" name="menuItem"/>
</jsp:include>
<div class="sui-container">
    <ul class="sui-breadcrumb">
        <li><a href="#">首页</a></li>
        <li><a href="#">手机数码、电脑办公</a></li>
        <li class="active">智能手机</li>
    </ul>
    <form class="sui-form form-search">
        <input type="text" class="input-medium search-query">
        <button type="submit" class="sui-btn btn-primary">搜索</button>
        <a href="/recruitment/add" target="_blank" class="sui-btn btn-large btn-primary">发布任务</a>
    </form>
    <table class="sui-table table-primary">
        <thead>
        <tr>
            <th>招募ID</th>
            <th>标题</th>
            <th>登记编号</th>
            <th>实验分期</th>
            <th>适应症状</th>
            <th>药物名称</th>
            <th>药物类型</th>
            <th>招募人数</th>
            <th>启始时间</th>
            <th>截至时间</th>
            <th>招募状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="recruitment-panel"></tbody>
    </table>
    <div class="pagination" style="text-align: right;margin-right: 20px">
    </div>
</div>
</body>
<script type="text/javascript" src="/static/js/util/ajax.js?_v=${version}"></script>
<script type="text/javascript" src="/static/js/recruitment/list-pc.js?_v=${version}"></script>
</html>