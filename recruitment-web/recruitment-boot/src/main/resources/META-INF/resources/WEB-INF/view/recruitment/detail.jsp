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
    <link rel="stylesheet" href="/static/css/recruitment/detail.css?_v=${version}">
</head>
<body>
<div class="page-group">
    <div class="page page-current" style="background-color: #FFF">
        <header class="bar bar-nav">
            <h1 class="title">任务详情</h1>
        </header>
        <div class="content">
            <h3>${recruitmentInfo.title}</h3>
            <div class="list-block">
                <ul>
                    <li class="item-content">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner">
                            <div class="item-title">登记编号</div>
                            <div class="item-after">${recruitmentInfo.registerCode}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner">
                            <div class="item-title">实验分期</div>
                            <div class="item-after">${recruitmentInfo.practiceStages}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner">
                            <div class="item-title">适应症状</div>
                            <div class="item-after">${recruitmentInfo.indication}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner">
                            <div class="item-title">药物名称</div>
                            <div class="item-after">${recruitmentInfo.drugName}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner">
                            <div class="item-title">药物类型</div>
                            <div class="item-after">${recruitmentInfo.drugType}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-media"><i class="icon icon-f7"></i></div>
                        <div class="item-inner">
                            <div class="item-title">招募人数</div>
                            <div class="item-after">${recruitmentInfo.recruitmentNumber}人</div>
                        </div>
                    </li>
                </ul>
            </div>
            <p>${recruitmentInfo.introduction}</p>
            <div class="buttons-tab">
                <a href="#treatmentPlan" class="tab-link active button">治疗方案</a>
                <a href="#screeningStandard" class="tab-link button">初筛要点</a>
                <a href="#entryCriteria" class="tab-link button">入拍标准</a>
                <a href="#researchCenterInfo" class="tab-link button">研究中心</a>
                <a href="#patientRights" class="tab-link button">患者权益</a>
            </div>
            <div class="content-block" style="padding: 0; margin: 0;">
                <div class="tabs">
                    <div id="treatmentPlan" class="tab active">
                        <div class="content-block" style="padding: 0; margin: 0">
                            <p>${recruitmentInfo.treatmentPlan}</p>
                        </div>
                    </div>
                    <div id="screeningStandard" class="tab">
                        <div class="content-block" style="padding: 0; margin: 0">
                            <p>${recruitmentInfo.screeningStandard}</p>
                        </div>
                    </div>
                    <div id="entryCriteria" class="tab">
                        <div class="content-block" style="padding: 0; margin: 0">
                            <p>${recruitmentInfo.entryCriteria}</p>
                        </div>
                    </div>
                    <div id="researchCenterInfo" class="tab">
                        <div class="content-block" style="padding: 0; margin: 0">
                            <div class="list-block">
                                <ul>
                                    <c:forEach items="${researchCenterListVO}" var="item">
                                        <li class="item-content">
                                            <div style="padding: 0 ;margin: 0" class="item-inner">
                                                <ul style="padding: 0">
                                                    <li style="padding: 0;color: #222">${item.name}</li>
                                                    <li style="padding: 0;color: #888">${item.address}</li>
                                                </ul>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div id="patientRights" class="tab">
                        <div class="content-block" style="padding: 0; margin: 0">
                            <p>${recruitmentInfo.patientRights}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${userInfo.userType.code == 2}">
            <nav class="bar bar-tab">
                <a href="javascript:void(0)" id="recommend_qrcode"
                   class="button button-fill button-warning">推荐二维码</a>
            </nav>
        </c:if>
        <c:if test="${userInfo.userType.code == 3}">
            <nav class="bar bar-tab">
                <a href="javascript:void(0)" id="sign_up_button"
                   class="button button-fill button-warning">报名</a>
            </nav>
        </c:if>
    </div>
    <div class="popup popup-qrcode">
        <a href="#" class="close-popup">
            <div class="content-block" id="qrCode"></div>
        </a>
    </div>
</div>
</body>
<input type="hidden" id="recruitmentId" value="${recruitmentInfo.recruitmentId}">
<input type="hidden" id="doctorId" value="${doctorId}">
<script type='text/javascript' src='/static/js/lib/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/qrcode.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/util/ajax.js?_v=${version}' charset="utf-8"></script>
<script type='text/javascript' src='/static/js/recruitment/detail.js?_v=${version}'
        charset="utf-8"></script>
</html>
