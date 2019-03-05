<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <title>管理员列表</title>
    <link href="/static/css/lib/sui.min.css" rel="stylesheet">
    <link href="/static/css/lib/list-pc.css?_v=${version}" rel="stylesheet">
    <script type="text/javascript"
            src="/static/js/lib/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/lib/sui.min.js"></script>
</head>
<body>
<jsp:include page="../components/navbar-pc.jsp">
    <jsp:param value="user" name="menuTarget"/>
    <jsp:param value="managerList" name="menuAction"/>
</jsp:include>
<div class="sui-container">
    <ul class="sui-breadcrumb">
        <li><a href="javascript:void(0)">用户管理</a></li>
        <li><a href="javascript:void(0)">管理员管理</a></li>
        <li class="active">管理员列表</li>
    </ul>
    <div id="tips-error" class="tips-error">
        <div class="sui-msg msg-error">
            <div class="msg-con">错误信息提示</div>
            <s class="msg-icon"></s>
        </div>
    </div>
    <form class="sui-form form-search form-horizontal">
        姓名：<input type="text" name="realName" placeholder="姓名" data-rules="maxlength=16">
        手机号：<input type="text" name="phoneLike" placeholder="手机号" data-rules="maxlength=32">
        <button type="submit" class="sui-btn btn-primary">搜索</button>
        <a href="/user/manager/add-pc" target="_blank" class="sui-btn btn-primary">添加管理员</a>
    </form>
    <table class="sui-table table-primary">
        <thead>
        <tr>
            <th>姓名</th>
            <th>手机号</th>
            <th>性别</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="manager-panel"></tbody>
    </table>
    <div class="pagination" style="text-align: right;margin-right: 20px">
    </div>
</div>
<div class="loading">
    <div class="sui-loading loading-inline">
        <i class="sui-icon icon-pc-loading"></i>
    </div>
</div>
</body>
<script type="text/javascript" src="/static/js/util/ajax.js?_v=${version}"></script>
<script type='text/javascript' src='/static/js/user/managerList-pc.js?_v=${version}'
        charset="utf-8"></script>
</html>