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
    <link rel="stylesheet" href="/static/css/recruitment/detail.css?_v=${version}">
</head>
<body>
<div class="page-group">
    <div class="page page-current">
        <header class="bar bar-nav">
            <h1 class="title">医生个人信息</h1>
        </header>
        <jsp:include page="../components/footer.jsp">
            <jsp:param value="me" name="menuItem"/>
        </jsp:include>
        <div class="content">
            <div class="list-block">
                <ul>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">姓名</div>
                            <div class="item-after">${userInfoVO.realName}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">性别</div>
                            <div class="item-after">${userInfoVO.gender.desc}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">手机号码</div>
                            <div class="item-after">${userInfoVO.phone}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">执业机构</div>
                            <div class="item-after">${doctorInfoVO.medicalInstitution}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">执业类别</div>
                            <div class="item-after">${doctorInfoVO.medicalCategory}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">地址</div>
                            <div class="item-after">${doctorInfoVO.address}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">状态</div>
                            <div class="item-after">${userInfoVO.status.desc}</div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>