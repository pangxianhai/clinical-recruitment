<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="/static/css/lib/sm.min.css">
</head>
<body>
<div class="page-group">
    <div class="page page-current">
        <header class="bar bar-nav">
            <h1 class="title">申请记录详情</h1>
        </header>
        <jsp:include page="../../components/footer.jsp">
            <jsp:param value="applicationList" name="menuItem"/>
        </jsp:include>
        <div class="content">
            ${applicationInfo}
        </div>
    </div>
</div>
</body>
</html>