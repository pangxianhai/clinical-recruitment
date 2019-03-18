<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <title>患者列表</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link href="/static/css/lib/list-pc.css?_v=${version}" rel="stylesheet">
    <script type="text/javascript"
            src="http://g.alicdn.com/sj/lib/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="http://g.alicdn.com/sj/dpl/1.5.1/js/sui.min.js"></script>
</head>
<body>
<jsp:include page="../components/navbar-pc.jsp">
    <jsp:param value="user" name="menuTarget"/>
    <jsp:param value="patientList" name="menuAction"/>
</jsp:include>
<div class="sui-container">
    <ul class="sui-breadcrumb">
        <li><a href="javascript:void(0)">用户管理</a></li>
        <li><a href="javascript:void(0)">患者管理</a></li>
        <li class="active">患者列表</li>
    </ul>
    <div id="tips-error" class="tips-error">
        <div class="sui-msg msg-error">
            <div class="msg-con">错误信息提示</div>
            <s class="msg-icon"></s>
        </div>
    </div>
    <form class="sui-form form-search">
        患者姓名：<input type="text" name="realName" placeholder="患者姓名" data-rules="maxlength=32">
        手机号：<input type="text" name="phoneLike" placeholder="手机号" data-rules="digits|maxlength=11">
        状态：
        <span class="sui-dropdown dropdown-bordered select">
            <span class="dropdown-inner">
                <a role="button" data-toggle="dropdown" href="#" class="dropdown-toggle">
                    <input name="status" type="hidden">
                    <i class="caret"></i>
                    <span>请选择</span>
                </a>
                <ul id="menu4" role="menu" aria-labelledby="drop4" class="sui-dropdown-menu">
                     <li role="presentation">
                        <a role="menuitem" tabindex="-1" href="javascript:void(0);"
                           value="">请选择</a>
                    </li>
                    <li role="presentation">
                        <a role="menuitem" tabindex="-1" href="javascript:void(0);"
                           value="1">正常</a>
                    </li>
                    <li role="presentation">
                        <a role="menuitem" tabindex="-1" href="javascript:void(0);"
                           value="2">冻结</a>
                    </li>
                </ul>
                </span>
        </span>
        <button type="submit" class="sui-btn btn-primary">搜索</button>
    </form>
    <table class="sui-table table-primary">
        <thead>
        <tr>
            <th>患者姓名</th>
            <th>手机号</th>
            <th>性别</th>
            <th>微信昵称</th>
            <th>地址</th>
            <th>年龄</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="patient-panel"></tbody>
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
<script type='text/javascript' src='/static/js/patient/list-pc.js?_v=${version}'
        charset="utf-8"></script>
</html>