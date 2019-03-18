<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <title>添加医生</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui-append.min.css" rel="stylesheet">
    <script type="text/javascript"
            src="http://g.alicdn.com/sj/lib/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="http://g.alicdn.com/sj/dpl/1.5.1/js/sui.min.js"></script>
</head>
<body>
<jsp:include page="../components/navbar-pc.jsp">
    <jsp:param value="user" name="menuTarget"/>
    <jsp:param value="doctorAdd" name="menuAction"/>
</jsp:include>
<div class="sui-container">
    <ul class="sui-breadcrumb">
        <li><a href="javascript:void(0)">用户管理</a></li>
        <li><a href="/patient/list-pc">患者管理</a></li>
        <li class="active">添加患者</li>
    </ul>
    <form id="addDoctorForm" class="sui-form form-horizontal" novalidate="novalidate">
        <div class="control-group">
            <label for="name" class="control-label">姓名：</label>
            <div class="controls">
                <input type="text" id="name" name="name" placeholder="姓名"
                       data-rules="required|maxlength=16">
            </div>
        </div>
        <div class="control-group">
            <label for="phone" class="control-label">手机号：</label>
            <div class="controls">
                <input type="text" id="phone" name="phone" placeholder="手机号码"
                       data-rules="required|digits|minlength=11|maxlength=11"
                       data-error-msg="请填写正确的手机号码">
            </div>
        </div>
        <div class="control-group doctor-address">
            <label for="phone" class="control-label">地址：</label>
            <input type="hidden" name="provinceId" value=""/>
            <input type="hidden" name="cityId" value=""/>
            <input type="hidden" name="districtId" value=""/>
            <div class="controls">
                 <span class="sui-dropdown dropdown-bordered">
                    <span class="dropdown-inner">
                        <a role="button" data-toggle="dropdown" href="javascript:void(0)"
                           class="dropdown-toggle">
                            <i class="caret"></i>
                            <span item="choiceText">选择地址</span>
                        </a>
                        <ul role="menu" aria-labelledby="drop1" class="sui-dropdown-menu">
                            <c:forEach var="regionVO" items="${regionVOList}">
                                <c:if test="${not empty regionVO.children}">
                                    <li role="presentation" class="dropdown-submenu">
                                        <a role="menuitem" tabindex="-1" href="#">
                                            <i class="sui-icon icon-angle-right pull-right"></i>${regionVO.regionName}
                                        </a>
                                        <ul class="sui-dropdown-menu">
                                            <c:forEach var="childRegionVO"
                                                       items="${regionVO.children}">
                                                <c:if test="${not empty regionVO.children}">
                                                    <li role="presentation"
                                                        class="dropdown-submenu">
                                                         <a role="menuitem" tabindex="-1" href="#">
                                                             <i class="sui-icon icon-angle-right pull-right"></i>${childRegionVO.regionName}
                                                         </a>
                                                         <ul class="sui-dropdown-menu">
                                                              <c:forEach var="grandChildRegionVO"
                                                                         items="${childRegionVO.children}">
                                                                  <li role="presentation">
                                                                      <a role="menuitem"
                                                                         tabindex="-1"
                                                                         href="javascript:void(0)"
                                                                         item="regionSelectItem"
                                                                         regionName="${regionVO.regionName} ${childRegionVO.regionName} ${grandChildRegionVO.regionName}"
                                                                         provinceId="${regionVO.regionId}"
                                                                         cityId="${childRegionVO.regionId}"
                                                                         districtId="${grandChildRegionVO.regionId}">
                                                                              ${grandChildRegionVO.regionName}
                                                                      </a>
                                                                  </li>
                                                              </c:forEach>
                                                         </ul>
                                                    </li>
                                                </c:if>
                                                <c:if test="${empty regionVO.children}">
                                                    <li role="presentation">
                                                        <a role="menuitem" tabindex="-1"
                                                           href="javascript:void(0)">${childRegionVO.regionName}</a>
                                                    </li>
                                                </c:if>
                                            </c:forEach>
                                         </ul>
                                    </li>
                                </c:if>
                                <c:if test="${empty regionVO.children}">
                                    <li role="presentation">
                                        <a role="menuitem" tabindex="-1"
                                           href="javascript:void(0)">${regionVO.regionName}</a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </span>
                </span>
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
            <label for="medicalInstitution" class="control-label">执业机构：</label>
            <div class="controls">
                <input type="text" id="medicalInstitution" name="medicalInstitution" placeholder="执业机构"
                       data-rules="required|maxlength=32">
            </div>
        </div>
        <div class="control-group">
            <label for="medicalCategory" class="control-label">执业类别：</label>
            <div class="controls">
                <input type="text" id="medicalCategory" name="age" placeholder="执业类别"
                       data-rules="required|maxlength=32">
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
<script type="text/javascript" src="/static/js/doctor/add-pc.js?_v=${version}"></script>
</html>