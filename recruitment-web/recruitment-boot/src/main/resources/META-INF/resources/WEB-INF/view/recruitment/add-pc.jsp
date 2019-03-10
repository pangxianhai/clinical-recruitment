<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <title>添加任务</title>
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
    </style>
</head>
<body>
<jsp:include page="../components/navbar-pc.jsp">
    <jsp:param value="recruitment" name="menuTarget"/>
    <jsp:param value="recruitmentAdd" name="menuAction"/>
</jsp:include>
<div class="sui-container">
    <ul class="sui-breadcrumb">
        <li><a href="#">任务管理</a></li>
        <li class="active">发布任务</li>
    </ul>
    <form id="addRecruitmentForm" class="sui-form form-horizontal" novalidate="novalidate">
        <div class="control-group">
            <label for="title" class="control-label">标题：</label>
            <div class="controls">
                <input type="text" id="title" name="title" placeholder="标题" data-rules="required">
            </div>
        </div>
        <div class="control-group">
            <label for="introductionArea" class="control-label">简介：</label>
            <div class="controls">
                <textarea id="introductionArea" name="introduction" data-rules="required"
                          style="height: 80px;"
                          class="input-xxlarge"></textarea>
            </div>
        </div>
        <div class="control-group">
            <label for="treatmentPlanArea" class="control-label">治疗方案：</label>
            <div class="controls">
                <textarea id="treatmentPlanArea" name="treatmentPlan" data-rules="required"
                          style="height: 80px;"
                          class="input-xxlarge"></textarea>
            </div>
        </div>
        <div class="control-group">
            <label for="screeningStandardArea" class="control-label">初筛要点：</label>
            <div class="controls">
                <textarea id="screeningStandardArea" name="screeningStandard" data-rules="required"
                          style="height: 80px;"
                          class="input-xxlarge"></textarea>
            </div>
        </div>
        <div class="control-group">
            <label for="entryCriteriaArea" class="control-label">入排标准：</label>
            <div class="controls">
                <textarea id="entryCriteriaArea" name="entryCriteria" data-rules="required"
                          style="height: 80px;"
                          class="input-xxlarge"></textarea>
            </div>
        </div>
        <div class="control-group">
            <label for="patientRightsArea" class="control-label">患者权益：</label>
            <div class="controls">
                <textarea id="patientRightsArea" name="patientRights" data-rules="required"
                          style="height: 80px;" class="input-xxlarge"></textarea>
            </div>
        </div>
        <div class="control-group">
            <label for="registerCode" class="control-label">登记编号：</label>
            <div class="controls">
                <input type="text" id="registerCode" name="registerCode" placeholder="登记编号"
                       data-rules="required">
            </div>
        </div>
        <div class="control-group">
            <label for="practiceStages" class="control-label">实验分期：</label>
            <div class="controls">
                <input type="text" id="practiceStages" name="practiceStages" placeholder="实验分期"
                       data-rules="required">
            </div>
        </div>
        <div class="control-group">
            <label for="indication" class="control-label">适应症状：</label>
            <div class="controls">
                <input type="text" id="indication" name="indication" placeholder="适应症状"
                       class="input-xxlarge"
                       data-rules="required">
            </div>
        </div>
        <div class="control-group">
            <label for="drugName" class="control-label">药物名称：</label>
            <div class="controls">
                <input type="text" id="drugName" name="drugName" placeholder="药物名称"
                       class="input-xxlarge"
                       data-rules="required">
            </div>
        </div>
        <div class="control-group">
            <label for="drugType" class="control-label">药物类型：</label>
            <div class="controls">
                <input type="text" id="drugType" name="drugType" placeholder="药物类型"
                       class="input-xxlarge"
                       data-rules="required">
            </div>
        </div>
        <div class="control-group">
            <label for="recruitmentNumber" class="control-label">招募人数：</label>
            <div class="controls">
                <input type="text" id="recruitmentNumber" name="recruitmentNumber"
                       data-rules="required|digits"
                       class="input-large"
                       placeholder="招募人数">
            </div>
        </div>
        <div class="control-group">
            <label for="startTime" class="control-label">启至时间：</label>
            <div class="controls">
                <input type="text" id="startTime" name="startTime" class="input-medium input-date"
                       data-toggle="datepicker" data-rules="required"
                       placeholder="启始时间">
                <span>-</span>
                <input type="text" id="stopTime" name="stopTime" class="input-medium input-date"
                       data-toggle="datepicker" data-rules="required"
                       placeholder="截至时间">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"></label>
            <div class="controls">
                <button id="addRecruitmentBtn"
                        type="submit"
                        class="sui-btn btn-primary">发布
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
<script type="text/javascript" src="/static/js/recruitment/add-pc.js?_v=${version}"></script>
</html>