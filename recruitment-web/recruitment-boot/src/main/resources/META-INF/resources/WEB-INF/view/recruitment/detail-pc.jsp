<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <style>
        .details {
            margin-top: 10px;
        }

        .span1 {
            text-align: right;
        }

        .span6 {
            width: 90%;
        }

        .operator {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<jsp:include page="../components/navbar-pc.jsp">
    <jsp:param value="recruitment" name="menuTarget"/>
    <jsp:param value="recruitmentList" name="menuAction"/>
</jsp:include>
<div class="sui-container">
    <ul class="sui-breadcrumb">
        <li><a href="javascript:void(0)">任务管理</a></li>
        <li><a href="/recruitment/list-pc">任务列表</a></li>
        <li class="active">任务详情</li>
    </ul>
    <div class="details">
        <div class="sui-row-fluid">
            <div class="span1">标题：</div>
            <div class="span3">${recruitmentVO.title}</div>
            <div class="span1">登记编号：</div>
            <div class="span3">${recruitmentVO.registerCode}</div>
            <div class="span1">实验分期：</div>
            <div class="span3">${recruitmentVO.practiceStages}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">适应症状：</div>
            <div class="span3">${recruitmentVO.indication}</div>
            <div class="span1">药物名称：</div>
            <div class="span3">${recruitmentVO.drugName}</div>
            <div class="span1">药物类型：</div>
            <div class="span3">${recruitmentVO.drugType}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">招募人数：</div>
            <div class="span3">${recruitmentVO.recruitmentNumber}</div>
            <div class="span1">启始时间：</div>
            <div class="span3">${recruitmentVO.startTime}&nbsp;至&nbsp;${recruitmentVO.stopTime}</div>
            <div class="span1">招募状态：</div>
            <div class="span3">${recruitmentVO.status.desc}</div>

        </div>
        <div class="sui-row-fluid">
            <div class="span1">简介：</div>
            <div class="span6">${recruitmentVO.introduction}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">治疗方案：</div>
            <div class="span6">${recruitmentVO.treatmentPlan}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">初筛要点：</div>
            <div class="span6">${recruitmentVO.screeningStandard}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">入排标准：</div>
            <div class="span6">${recruitmentVO.entryCriteria}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">患者权益：</div>
            <div class="span6">${recruitmentVO.patientRights}</div>
        </div>
        <div class="sui-row-fluid">
            <div class="span1">研究中心：</div>
            <div class="span6">
                <c:forEach items="${researchCenterListVO}" var="center">
                    <div>${center.address} ${center.name}</div>
                </c:forEach>
            </div>
        </div>
        <div class="sui-row-fluid operator">
            <div class="span1"></div>
            <div class="span6">
                <a class="sui-btn btn-small btn-success"
                   href="/recruitment/updatePc/${recruitmentVO.recruitmentId}">
                    更新
                </a>
                <c:if test="${recruitmentVO.status.code == 0}">
                    <a class="sui-btn btn-small btn-info" href="javascript:void(0)"
                       recruitmentId="${recruitmentVO.recruitmentId}" item="begin">
                        开始
                    </a>
                </c:if>
                <c:if test="${recruitmentVO.status.code == 1}">
                    <a class="sui-btn btn-small btn-danger" href="javascript:void(0)"
                       recruitmentId="${recruitmentVO.recruitmentId}" item="stop">
                        结束
                    </a>
                </c:if>
                <c:if test="${recruitmentVO.status.code == 2}">
                    <a class="sui-btn btn-small btn-warning" href="javascript:void(0)"
                       recruitmentId="${recruitmentVO.recruitmentId}" item="begin">
                        重新开始
                    </a>
                </c:if>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/static/js/util/ajax.js?_v=${version}"></script>
<script src="/static/js/recruitment/detail-pc.js?_v=${version}"></script>
</body>
</html>