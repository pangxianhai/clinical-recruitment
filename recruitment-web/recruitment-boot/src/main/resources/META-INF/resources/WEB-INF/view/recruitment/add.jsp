<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="/static/css/lib/sm.min.css">
    <link rel="stylesheet" href="/static/css/recruitment/add.css?_v=${version}">
</head>
<body>
<div class="page-group">
    <form id="recruitmentAddForm">
        <div class="page page-current">
            <header class="bar bar-nav">
                <h1 class="title">发布任务</h1>
            </header>
            <div class="content">
                <div class="list-block">
                    <ul>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-input">
                                        <input name="title" type="text" placeholder="标题">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-input">
                                        <input name="registerCode" type="text" placeholder="登记编号">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-input">
                                        <input name="practiceStages" type="text" placeholder="实验分期">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-input">
                                        <input name="indication" type="text" placeholder="适应症状">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-input">
                                        <input name="drugName" type="text" placeholder="药物名称">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-input">
                                        <input name="drugType" type="text" placeholder="药物类型">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-input">
                                        <input name="recruitmentNumber" type="text"
                                               placeholder="招募人数">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-input">
                                        <input name="startTime" data-toggle='date' type="text"
                                               placeholder="开始时间">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-input">
                                        <input name="stopTime" data-toggle='date' type="text"
                                               placeholder="截至时间">
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="content-block">
                    <div class="content-block">
                        <p>
                            <a id="open-introduction"
                               href="javascript:void(0)"
                               class="button button-fill ">下一步</a>
                        </p>
                    </div>
                </div>
            </div>
            <jsp:include page="../components/footer.jsp">
                <jsp:param value="recruitmentList" name="menuItem"/>
            </jsp:include>
        </div>
        <div class="popup popup-introduction">
            <header class="bar bar-nav">
                <h1 class="title">简介</h1>
            </header>
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-input">
                                    <textarea name="introduction" placeholder="简介"></textarea>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <div class="content-block">
                    <p>
                        <a id="open-treatment-plan"
                           href="javascript:void(0)"
                           class="button button-fill">下一步</a>
                    </p>
                </div>
            </div>
        </div>
        <div class="popup popup-treatment-plan">
            <header class="bar bar-nav">
                <h1 class="title">治疗方案</h1>
            </header>
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-input">
                                    <textarea name="treatmentPlan" placeholder="治疗方案"></textarea>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <div class="content-block">
                    <p>
                        <a id="open-screening-standard"
                           href="javascript:void(0)"
                           class="button button-fill">下一步</a>
                    </p>
                </div>
            </div>
        </div>
        <div class="popup popup-screening-standard">
            <header class="bar bar-nav">
                <h1 class="title">初筛要点</h1>
            </header>
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-input">
                                    <textarea name="screeningStandard"
                                              placeholder="初筛要点"></textarea>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <div class="content-block">
                    <p>
                        <a id="open-entry-criteria"
                           href="javascript:void(0)"
                           class="button button-fill">下一步</a>
                    </p>
                </div>
            </div>
        </div>
        <div class="popup popup-entry-criteria">
            <header class="bar bar-nav">
                <h1 class="title">入排标准</h1>
            </header>
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-input">
                                    <textarea name="entryCriteria" placeholder="入排标准"></textarea>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <div class="content-block">
                    <p>
                        <a id="open-patient-rights"
                           href="javascript:void(0)"
                           class="button button-fill">下一步</a>
                    </p>
                </div>
            </div>
        </div>
        <div class="popup popup-patient-rights">
            <header class="bar bar-nav">
                <h1 class="title">患者权益</h1>
            </header>
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-input">
                                    <textarea name="patientRights" placeholder="患者权益"></textarea>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <div class="content-block">
                    <p>
                        <a id="open-research-center"
                           href="javascript:void(0)"
                           class="button button-fill">下一步</a>
                    </p>
                </div>
            </div>
        </div>
        <div class="popup popup-research-center">
            <header class="bar bar-nav">
                <h1 class="title">研究中心</h1>
            </header>
            <div class="list-block">
                <ul class="research-center-list">
                    <li id="center-node">
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-input" style="float: left;width: 70%">
                                    <input name="researchCenterName" placeholder="研究中心名称"/>
                                    <input item="choice-center-address" name="researchCenterAddress"
                                           placeholder="选择研究中心地址"/>
                                </div>
                                <div style="float: left;width: 30%">
                                    <p class="buttons-row">
                                        <a href="javascript:void(0)"
                                           item="add-center-button"
                                           class="button button-round active">添加</a>
                                        <a href="javascript:void(0)"
                                           item="delete-center-button"
                                           class="button button-round">删除</a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <div class="content-block">
                    <p><a id="addSubmit" href="javascript:void(0)" class="button button-fill">提交</a>
                    </p>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
<script type='text/javascript' src='/static/js/lib/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/sm-city-picker.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/util/ajax.js?_v=${version}' charset="utf-8"></script>
<script type='text/javascript' src='/static/js/recruitment/add.js?_v=${version}'
        charset="utf-8"></script>
</html>
