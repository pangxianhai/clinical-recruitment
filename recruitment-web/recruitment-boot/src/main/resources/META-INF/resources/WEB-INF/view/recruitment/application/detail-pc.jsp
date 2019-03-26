<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <title>申请记录详情</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui-append.min.css" rel="stylesheet">
    <link href="/static/css/lib/list-pc.css?_v=${version}" rel="stylesheet">
    <script type="text/javascript"
            src="http://g.alicdn.com/sj/lib/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="http://g.alicdn.com/sj/dpl/1.5.1/js/sui.min.js"></script>
    <style>
        .details {
            margin-top: 10px;
        }

        .details .title {
            margin: 8px auto 8px auto;
            font-size: 14px;
            font-weight: 500;
            color: #28a3ef;
        }

        .span1 {
            text-align: right;
        }

        .span6 {
            width: 90%;
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
        <li><a href="javascript:void(0)">任务管理</a></li>
        <li><a href="/recruitmentapplication/list-pc">申请记录列表</a></li>
        <li class="active">申请记录详情</li>
    </ul>
    <div class="details">
        <div class="title">任务信息</div>
        <div class="sui-row-fluid">
            <div class="span1">标题：</div>
            <div class="span3">${applicationVO.recruitmentVO.title}</div>
            <div class="span1">登记编号：</div>
            <div class="span3">${applicationVO.recruitmentVO.registerCode}</div>
            <div class="span1">招募人数：</div>
            <div class="span3">${applicationVO.recruitmentVO.recruitmentNumber}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">实验分期：</div>
            <div class="span3">${applicationVO.recruitmentVO.practiceStages}</div>
            <div class="span1">适应症状：</div>
            <div class="span3">${applicationVO.recruitmentVO.indication}</div>
            <div class="span1">启始时间：</div>
            <div class="span3">${applicationVO.recruitmentVO.startTime}&nbsp;至&nbsp;${applicationVO.recruitmentVO.stopTime}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">药物名称：</div>
            <div class="span3">${applicationVO.recruitmentVO.drugName}</div>
            <div class="span1">药物类型：</div>
            <div class="span3">${applicationVO.recruitmentVO.drugType}</div>
            <div class="span1">招募状态：</div>
            <div class="span3">${applicationVO.recruitmentVO.status.desc}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">简介：</div>
            <div class="span6">${applicationVO.recruitmentVO.introduction}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">治疗方案：</div>
            <div class="span6">${applicationVO.recruitmentVO.treatmentPlan}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">初筛要点：</div>
            <div class="span6">${applicationVO.recruitmentVO.screeningStandard}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">入排标准：</div>
            <div class="span6">${applicationVO.recruitmentVO.entryCriteria}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">患者权益：</div>
            <div class="span6">${applicationVO.recruitmentVO.patientRights}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">研究中心：</div>
            <div class="span6">
                <c:forEach items="${applicationVO.recruitmentVO.researchCenterVOList}" var="center">
                    <div>${center.address} ${center.name}</div>
                </c:forEach>
            </div>
        </div>
        <div class="title">患者信息</div>
        <div class="sui-row-fluid">
            <div class="span1">姓名：</div>
            <div class="span3">${applicationVO.patientVO.userInfoVO.realName}</div>
            <div class="span1">手机号：</div>
            <div class="span3">${applicationVO.patientVO.userInfoVO.phone}</div>
            <div class="span1">性别：</div>
            <div class="span3">${applicationVO.patientVO.userInfoVO.gender.desc}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">年龄：</div>
            <div class="span3">${applicationVO.patientVO.age}</div>
            <div class="span1">地址：</div>
            <div class="span3">${applicationVO.patientVO.address}</div>
            <div class="span1">状态：</div>
            <div class="span3">${applicationVO.patientVO.userInfoVO.status.desc}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">申请时间：</div>
            <div class="span3">${applicationVO.applicationTime}</div>
            <div class="span1">申请状态：</div>
            <div class="span3">${applicationVO.status.desc}</div>
        </div>
        <div class="title">推荐医生信息</div>
        <c:if test="${empty applicationVO.doctorInfoVO}">
            <div class="sui-row-fluid">
                <div class="span1">无推荐医生</div>
            </div>
        </c:if>
        <c:if test="${!empty applicationVO.doctorInfoVO}">
            <div class="sui-row-fluid">
                <div class="span1">姓名：</div>
                <div class="span3">${applicationVO.doctorInfoVO.userInfoVO.realName}</div>
                <div class="span1">手机号：</div>
                <div class="span3">${applicationVO.doctorInfoVO.userInfoVO.phone}</div>
                <div class="span1">性别：</div>
                <div class="span3">${applicationVO.doctorInfoVO.userInfoVO.gender.desc}</div>
                <div class="span1">地址：</div>
                <div class="span3">${applicationVO.doctorInfoVO.address}</div>
            </div>
            <div class="sui-row-fluid">
                <div class="span1">执业机构：</div>
                <div class="span3">${applicationVO.doctorInfoVO.medicalInstitution}</div>
                <div class="span1">执业类别：</div>
                <div class="span3">${applicationVO.doctorInfoVO.medicalCategory}</div>
                <div class="span1">地址：</div>
                <div class="span3">${applicationVO.doctorInfoVO.address}</div>
            </div>
            <div class="sui-row-fluid">
                <div class="span1">状态：</div>
                <div class="span3">${applicationVO.doctorInfoVO.userInfoVO.status.desc}</div>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>