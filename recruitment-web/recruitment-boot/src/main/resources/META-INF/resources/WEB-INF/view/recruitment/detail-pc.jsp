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
        <li><a href="javascript:void(0)">任务管理</a></li>
        <li href="/recruitment/list-pc">任务列表</li>
        <li class="active">任务详情</li>
    </ul>
</div>
</body>
</html>