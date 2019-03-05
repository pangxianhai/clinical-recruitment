<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <title>任务列表</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui-append.min.css" rel="stylesheet">
    <link href="/static/css/lib/list-pc.css?_v=${version}" rel="stylesheet">
    <script type="text/javascript"
            src="http://g.alicdn.com/sj/lib/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="http://g.alicdn.com/sj/dpl/1.5.1/js/sui.min.js"></script>
</head>
<body>
<jsp:include page="../components/navbar-pc.jsp">
    <jsp:param value="recruitment" name="menuTarget"/>
    <jsp:param value="recruitmentList" name="menuAction"/>
</jsp:include>
<div class="sui-container">
    <ul class="sui-breadcrumb">
        <li><a href="#">任务管理</a></li>
        <li class="active">任务列表</li>
    </ul>
    <div id="tips-error" class="tips-error">
        <div class="sui-msg msg-error">
            <div class="msg-con">错误信息提示</div>
            <s class="msg-icon"></s>
        </div>
    </div>
    <form class="sui-form form-search form-horizontal">
        标题：<input type="text" name="title" placeholder="标题" data-rules="maxlength=32">
        登记编号：<input type="text" name="registerCode" placeholder="登记编号" data-rules="maxlength=32">
        适应症状：<input type="text" name="indication" placeholder="适应症状" data-rules="maxlength=32">
        启始时间：
        <input type="text" name="startTimeBegin" class="input-medium input-date"
               data-toggle="datepicker" style="margin-right: 0"
               placeholder="启始时间">
        <span>-</span>
        <input type="text" name="startTimeEnd" class="input-medium input-date"
               data-toggle="datepicker"
               placeholder="截至时间">
        <div style="height: 25px"></div>
        截至时间：
        <input type="text" name="stopTimeBegin" class="input-medium input-date"
               data-toggle="datepicker" style="margin-right: 0"
               placeholder="启始时间">
        <span>-</span>
        <input type="text" name="stopTimeEnd" class="input-medium input-date"
               data-toggle="datepicker"
               placeholder="截至时间">
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
                           value="0">未招募</a>
                    </li>
                    <li role="presentation">
                        <a role="menuitem" tabindex="-1" href="javascript:void(0);"
                           value="1">进行中</a>
                    </li>
                    <li role="presentation">
                        <a role="menuitem" tabindex="-1" href="javascript:void(0);"
                           value="2">已完成</a>
                    </li>
                </ul>
                </span>
        </span>
        <button type="submit" class="sui-btn btn-primary">搜索</button>
        <a href="/recruitment/add-pc" target="_blank" class="sui-btn btn-primary">发布任务</a>
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
        <tbody id="recruitment-panel">
        </tbody>
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
<script type="text/javascript" src="/static/js/recruitment/list-pc.js?_v=${version}"></script>
</html>