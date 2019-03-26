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
</head>
<body>
<div class="page-group">
    <div class="page page-current">
        <header class="bar bar-nav">
            <c:if test="${'application' == action}">
                <h1 class="title">招募报名</h1>
            </c:if>
            <c:if test="${'application' != action}">
                <h1 class="title">患者信息注册</h1>
            </c:if>
        </header>
        <div class="content">
            <div class="list-block">
                <ul>
                    <c:if test="${not empty recruitmentVO}">
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">项目</div>
                                    <div class="item-input">
                                        <input disabled="disabled" type="text"
                                               value="${recruitmentVO.title}"/>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </c:if>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-name"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">姓名</div>
                                <div class="item-input">
                                    <input id="nameInput" type="text" placeholder="请填姓名"/>
                                </div>
                            </div>
                        </div>
                    </li>
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
                                <div class="item-title label">年龄</div>
                                <div class="item-input">
                                    <input id="ageInput" type="text" placeholder="请填年龄"/>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><i class="icon icon-form-name"></i></div>
                            <div class="item-inner">
                                <div class="item-title label">性别</div>
                                <div class="item-input">
                                    <select id="genderInput">
                                        <option value="1">男</option>
                                        <option value="2">女</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <div class="row" style="margin-left: auto">
                    <div class="col-50">
                        <a href="/recruitment/list"
                           class="button button-big button-fill button-danger external">取消</a>
                    </div>
                    <div class="col-50">
                        <c:if test="${'application' == action}">
                            <a href="javascript:void(0)" id="registerButton"
                               class="button button-big button-fill button-success">报名</a>
                        </c:if>
                        <c:if test="${'application' != action}">
                            <a href="javascript:void(0)" id="registerButton"
                               class="button button-big button-fill button-success">注册</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" value="${openId}" id="openId">
<input type="hidden" value="${nickname}" id="nickname">
<input type="hidden" value="${userType}" id="userTypeValue">
<input type="hidden" value="${redirectURL}" id="redirectURL">
<input type="hidden" value="${action}" id="action">
<script type='text/javascript' src='/static/js/lib/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/sm-city-picker.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/patient/register.js?_v=${version}'
        charset="utf-8"></script>
<script type='text/javascript' src='/static/js/util/ajax.js?_v=${version}' charset="utf-8"></script>
</body>
</html>