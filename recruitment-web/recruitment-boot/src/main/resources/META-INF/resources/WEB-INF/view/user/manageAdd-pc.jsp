<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <title>添加管理员</title>
    <link href="/static/css/lib/sui.min.css" rel="stylesheet">
    <script type="text/javascript"
            src="/static/js/lib/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/lib/sui.min.js"></script>
</head>
<body>
<jsp:include page="../components/navbar-pc.jsp">
    <jsp:param value="user" name="menuTarget"/>
    <jsp:param value="managerAdd" name="menuAction"/>
</jsp:include>
<div class="sui-container">
    <ul class="sui-breadcrumb">
        <li><a href="javascript:void(0)">用户管理</a></li>
        <li><a href="/user/manager/list-pc">管理员管理</a></li>
        <li class="active">添加管理员</li>
    </ul>
    <form id="addManagerForm" class="sui-form form-horizontal" novalidate="novalidate">
        <div class="control-group">
            <label for="realName" class="control-label">姓名：</label>
            <div class="controls">
                <input type="text" id="realName" name="realName" placeholder="姓名"
                       data-rules="required|maxlength=16">
            </div>
        </div>
        <div class="control-group">
            <label for="phone" class="control-label">手机号：</label>
            <div class="controls">
                <input type="text" id="phone" name="phone" placeholder="手机号码"
                       data-rules="required|maxlength=16">
            </div>
        </div>
        <div class="control-group">
            <label for="password" class="control-label">密码：</label>
            <div class="controls">
                <input type="text" id="password" name="password" placeholder="密码"
                       data-rules="required|maxlength=16">
            </div>
        </div>
        <div class="control-group">
            <label for="rePassword" class="control-label">重复密码：</label>
            <div class="controls">
                <input type="text" id="rePassword" name="rePassword" placeholder="重复密码"
                       data-rules="required|maxlength=16">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">性别：</label>
            <div class="controls">
                <label data-toggle="radio" class="radio-pretty inline">
                    <input type="radio" name="gender" value="1" data-rules="required"><span>男</span>
                </label>
                <label data-toggle="radio" class="radio-pretty inline">
                    <input type="radio" name="gender" value="2" data-rules="required"><span>女</span>
                </label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"></label>
            <div class="controls">
                <button type="submit" class="sui-btn btn-primary">添加</button>
            </div>
        </div>
    </form>
</div>
</body>
<script type="text/javascript" src="/static/js/util/ajax.js?_v=${version}"></script>
</html>