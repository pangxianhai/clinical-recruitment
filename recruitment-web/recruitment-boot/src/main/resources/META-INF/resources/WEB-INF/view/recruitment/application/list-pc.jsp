<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <title>任务列表</title>
    <link href="/static/css/lib/sui.min.css" rel="stylesheet">
    <link href="/static/css/lib/sui-append.min.css" rel="stylesheet">
    <script type="text/javascript"
            src="/static/js/lib/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/lib/sui.min.js"></script>
    <style>
        .form-search input {
            margin-right: 20px;
        }

        .sui-breadcrumb {
            margin: 0;
            padding: 8px 0 0 0;
        }

        .tips-error {
            height: 24px;
            text-align: center;
            margin-bottom: 10px;
        }

        .tips-error .msg-error {
            display: none;
        }

        .loading {
            position: absolute;
            top: 0;
            left: 0;
            background: #000;
            width: 100%;
            height: 100%;
            text-align: center;
            opacity: 0.6;
            filter: alpha(opacity=60);
            display: none;
        }

        .loading .sui-loading {
            margin-top: 253px;
        }
    </style>
</head>
<body>
<jsp:include page="../../components/navbar-pc.jsp">
    <jsp:param value="recruitment" name="menuTarget"/>
    <jsp:param value="recruitmentApplication" name="menuAction"/>
</jsp:include>
<div class="sui-container">
    <ul class="sui-breadcrumb">
        <li><a href="#">任务管理</a></li>
        <li class="active">申请记录列表</li>
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
        医生：
        <input id="doctor-select" type="text" placeholder="请输入医生姓名"
               autocomplete="off">
        <input type="hidden" name="doctorId" id="doctorId">
        患者：
        <input id="patient-select" type="text" placeholder="请输入患者姓名"
               autocomplete="off">
        <input type="hidden" name="patientId" id="patientId">
        <div style="height: 25px"></div>
        申请时间：
        <input type="text" name="startTimeBegin" class="input-medium input-date"
               data-toggle="datepicker" style="margin-right: 0"
               placeholder="开始日期">
        <span>-</span>
        <input type="text" name="startTimeEnd" class="input-medium input-date"
               data-toggle="datepicker"
               placeholder="结束日期">
        申请状态：
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
    </form>
    <table class="sui-table table-primary">
        <thead>
        <tr>
            <th>申请记录ID</th>
            <th>标题</th>
            <th>登记编号</th>
            <th>患者</th>
            <th>医生</th>
            <th>申请状态</th>
            <th>申请时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="application-panel">
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
<script type="text/javascript"
        src="/static/js/recruitment/application/list-pc.js?_v=${version}"></script>
</html>