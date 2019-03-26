<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <title>更新任务</title>
    <link href="http://g.alicdn.com/sj/sui-editor/0.0.2/editor/css/sui-editor.css" rel="stylesheet">
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui-append.min.css" rel="stylesheet">
    <script type="text/javascript"
            src="http://g.alicdn.com/sj/lib/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="http://g.alicdn.com/sj/dpl/1.5.1/js/sui.min.js"></script>
    <style>
        .sui-form {
            margin: auto;
            width: 90%
        }

        .sui-form input {
            width: 250px;
        }

        #addRecruitmentForm textarea {
            height: auto;
            resize: vertical;
        }

        .research-center .controls {
            display: block !important;
            margin-bottom: 8px;
            margin-top: 8px;
        }
    </style>
</head>
<body>
<jsp:include page="../components/navbar-pc.jsp">
    <jsp:param value="recruitment" name="menuTarget"/>
    <jsp:param value="recruitmentAdd" name="menuAction"/>
</jsp:include>
<div class="sui-container">
    <ul class="sui-breadcrumb">
        <li><a href="/recruitment/list-pc">任务管理</a></li>
        <li class="active">更新任务</li>
    </ul>
    <form id="updateRecruitmentForm" class="sui-form form-horizontal" novalidate="novalidate">
        <input type="hidden" name="recruitmentId" value="${recruitmentVO.recruitmentId}">
        <div class="control-group">
            <label for="title" class="control-label">标题：</label>
            <div class="controls">
                <input type="text" id="title" name="title" value="${recruitmentVO.title}"
                       placeholder="标题">
            </div>
        </div>
        <div class="control-group">
            <label for="registerCode" class="control-label">登记编号：</label>
            <div class="controls">
                <input type="text" id="registerCode" name="registerCode"
                       value="${recruitmentVO.registerCode}" placeholder="登记编号">
            </div>
        </div>
        <div class="control-group">
            <label for="practiceStages" class="control-label">实验分期：</label>
            <div class="controls">
                <input type="text" id="practiceStages" name="practiceStages" placeholder="实验分期"
                       value="${recruitmentVO.practiceStages}">
            </div>
        </div>
        <div class="control-group">
            <label for="indication" class="control-label">适应症状：</label>
            <div class="controls">
                <input type="text" id="indication" name="indication" placeholder="适应症状"
                       class="input-xxlarge" value="${recruitmentVO.indication}">
            </div>
        </div>
        <div class="control-group">
            <label for="drugName" class="control-label">药物名称：</label>
            <div class="controls">
                <input type="text" id="drugName" name="drugName" placeholder="药物名称"
                       class="input-xxlarge" value="${recruitmentVO.drugName}">
            </div>
        </div>
        <div class="control-group">
            <label for="drugType" class="control-label">药物类型：</label>
            <div class="controls">
                <input type="text" id="drugType" name="drugType" placeholder="药物类型"
                       class="input-xxlarge" value="${recruitmentVO.drugType}">
            </div>
        </div>
        <div class="control-group">
            <label for="recruitmentNumber" class="control-label">招募人数：</label>
            <div class="controls">
                <input type="text" id="recruitmentNumber" name="recruitmentNumber"
                       data-rules="digits"
                       class="input-large" value="${recruitmentVO.recruitmentNumber}"
                       placeholder="招募人数">
            </div>
        </div>
        <div class="control-group">
            <label for="introductionArea" class="control-label">简介：</label>
            <div class="controls">
                <textarea id="introductionArea" name="introduction"
                          class="input-xxlarge">${recruitmentVO.introduction}</textarea>
            </div>
        </div>
        <div class="control-group">
            <label for="treatmentPlanArea" class="control-label">治疗方案：</label>
            <div class="controls">
                <textarea id="treatmentPlanArea" name="treatmentPlan"
                          class="input-xxlarge">${recruitmentVO.treatmentPlan}</textarea>
            </div>
        </div>
        <div class="control-group">
            <label for="screeningStandardArea" class="control-label">初筛要点：</label>
            <div class="controls">
                <textarea id="screeningStandardArea" name="screeningStandard"
                          class="input-xxlarge">${recruitmentVO.screeningStandard}</textarea>
            </div>
        </div>
        <div class="control-group">
            <label for="entryCriteriaArea" class="control-label">入排标准：</label>
            <div class="controls">
                <textarea id="entryCriteriaArea" name="entryCriteria"
                          class="input-xxlarge">${recruitmentVO.entryCriteria}</textarea>
            </div>
        </div>
        <div class="control-group">
            <label for="patientRightsArea" class="control-label">患者权益：</label>
            <div class="controls">
                <textarea id="patientRightsArea" name="patientRights"
                          class="input-xxlarge">${recruitmentVO.patientRights}</textarea>
            </div>
        </div>
        <div class="control-group">
            <label for="startTime" class="control-label">启至时间：</label>
            <div class="controls">
                <input type="text" id="startTime" name="startTime" class="input-medium input-date"
                       data-toggle="datepicker" autocomplete="off"
                       value="${recruitmentVO.startTime}"
                       placeholder="启始时间">
                <span>-</span>
                <input type="text" id="stopTime" name="stopTime" class="input-medium input-date"
                       data-toggle="datepicker" autocomplete="off"
                       value="${recruitmentVO.stopTime}"
                       placeholder="截至时间">
            </div>
        </div>
        <div class="control-group research-center">
            <label class="control-label">研究中心：</label>
            <c:forEach items="${researchCenterListVO}" var="center">
                <div class="controls">
                    <input type="text" name="centerName" value="${center.name}" placeholder="中心名称"/>
                    <input type="hidden" name="centerId" value="${center.centerId}"
                           provinceId="${center.provinceId}" cityId="${center.cityId}"
                           districtId="${center.districtId}"/>
                    <span class="region-select"></span>
                    <a href="javascript:void(0);" class="sui-btn btn-bordered btn-primary">
                        <i class="sui-icon icon-touch-plus-circle"></i>
                    </a>
                    <a href="javascript:void(0);" class="sui-btn btn-bordered btn-danger">
                        <i class="sui-icon icon-touch-minus-circle"></i>
                    </a>
                </div>
            </c:forEach>
        </div>
        <div class="control-group">
            <label class="control-label"></label>
            <div class="controls">
                <button id="addRecruitmentBtn"
                        type="submit"
                        class="sui-btn btn-primary">更新
                </button>
                <button id="cancelRecruitmentBtn"
                        type="reset"
                        class="sui-btn">取消
                </button>
            </div>
        </div>
    </form>
</div>
</body>
<script type="text/javascript"
        src="http://g.alicdn.com/sj/sui-editor/0.0.2/sui-editor.config.js"></script>
<script type="text/javascript"
        src="http://g.alicdn.com/sj/sui-editor/0.0.2/editor/js/sui-editor.js"></script>
<script type="text/javascript" src="/static/js/util/ajax.js?_v=${version}"></script>
<script type="text/javascript" src="/static/js/region/regionSelect-pc.js?_v=${version}"></script>
<script type="text/javascript" src="/static/js/recruitment/update-pc.js?_v=${version}"></script>
</html>