<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <title>管理员登陆</title>
    <link href="/static/css/lib/sui.min.css" rel="stylesheet">
    <script type="text/javascript"
            src="/static/js/lib/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/lib/sui.min.js"></script>
    <style>
        .sui-container {
            text-align: center;
        }

        .sui-form {
            width: 430px;
            margin: 100px auto;
        }
    </style>
</head>
<body>
<div class="sui-container">
    <form id="manageLoginForm" class="sui-form form-horizontal" novalidate="novalidate">
        <p class="sui-text-xlarge" style="text-align: left;padding-left: 18px">临床试验招募系统管理员登陆</p>
        <div class="control-group">
            <label for="inputPhone" class="control-label">手机号：</label>
            <div class="controls">
                <input type="text" id="inputPhone" name="phone" placeholder="手机号"
                       data-rules="required|digits|minlength=11|maxlength=11"
                       data-error-msg="请填写正确的手机号码">
            </div>
        </div>
        <div class="control-group">
            <label for="inputPassword" class="control-label">密码：</label>
            <div class="controls">
                <input type="password" id="inputPassword" name="password" placeholder="密码"
                       data-rules="required|maxlength=16">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label"></label>
            <div class="controls">
                <button id="manageLoginBtn" type="submit" class="sui-btn btn-primary">登陆</button>
            </div>
        </div>
    </form>
</div>
</body>
<input id="redirectURL" type="hidden" value="${redirectURL}">
<script type="text/javascript" src="/static/js/util/ajax.js?_v=${version}"></script>
<script type="text/javascript" src="/static/js/user/manageLogin.js?_v=${version}"></script>
</html>