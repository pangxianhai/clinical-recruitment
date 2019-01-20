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
    <link rel="stylesheet" href="/static/css/lib/sm-extend.min.css">
    <style>

    </style>
</head>
<body>
<div class="page-group">
    <div class="page page-current">
        <header class="bar bar-nav">
            <h1 class="title">医生信息注册</h1>
        </header>
        <div class="content">
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-name"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">手机号</div>
                                <div class="item-input">
                                    <input id="phoneInput" type="text" placeholder="请填写您的手机号码">
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-name"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">地址</div>
                                <div class="item-input">
                                    <input id="addressInput" type="text" placeholder="请选择您的地址"/>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-name"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">执业机构</div>
                                <div class="item-input">
                                    <input id="medicalInstitutionInput" type="text"
                                           placeholder="请填写执业机构"/>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-name"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">执业类别</div>
                                <div class="item-input">
                                    <input id="medicalCategoryInput" type="text"
                                           placeholder="请填写执业类型">
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <div class="row" style="margin-left: auto">
                    <a href="javascript:void(0)" id="registerButton"
                       class="button button-big button-fill button-success">注册</a>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" value="${userType}" id="userTypeValue">
<input type="hidden" value="${redirectURL}" id="redirectURL">
<script type='text/javascript' src='/static/js/lib/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/sm-city-picker.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/doctor/register.js?_v=${version}' charset="utf-8"></script>
<script type='text/javascript' src='/static/js/util/ajax.js?_v=${version}' charset="utf-8"></script>
</body>
</html>