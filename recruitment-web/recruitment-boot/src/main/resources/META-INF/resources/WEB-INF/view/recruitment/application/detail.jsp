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
            <h1 class="title">申请记录详情</h1>
        </header>
        <jsp:include page="../../components/footer.jsp">
            <jsp:param value="applicationList" name="menuItem"/>
        </jsp:include>
        <div class="content">
            <div class="content-block-title">患者信息</div>
            <div class="list-block">
                <ul>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">患者姓名</div>
                            <div class="item-after">${applicationVO.patientVO.userInfoVO.realName}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">患者手机号码</div>
                            <div class="item-after">${applicationVO.patientVO.userInfoVO.phone}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">患者性别</div>
                            <div class="item-after">${applicationVO.patientVO.userInfoVO.gender.desc}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">患者年龄</div>
                            <div class="item-after">${applicationVO.patientVO.age}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">患者地址</div>
                            <div class="item-after">${applicationVO.patientVO.address}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">患者地址</div>
                            <div class="item-after">${applicationVO.patientVO.address}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">推荐医生</div>
                            <div class="item-after">${applicationVO.doctorInfoVO.userInfoVO.realName}</div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block-title">招募信息</div>
            <div class="list-block">
                <ul>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">登记编号</div>
                            <div class="item-after">${applicationVO.recruitmentVO.registerCode}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">实验分期</div>
                            <div class="item-after">${applicationVO.recruitmentVO.practiceStages}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">适应症状</div>
                            <div class="item-after">${applicationVO.recruitmentVO.indication}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">药物名称</div>
                            <div class="item-after">${applicationVO.recruitmentVO.drugName}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">药物类型</div>
                            <div class="item-after">${applicationVO.recruitmentVO.drugType}</div>
                        </div>
                    </li>
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">招募人数</div>
                            <div class="item-after">${applicationVO.recruitmentVO.recruitmentNumber}人</div>
                        </div>
                    </li>
                </ul>
            </div>
            <p>${applicationVO.recruitmentVO.introduction}</p>
            <div class="buttons-tab">
                <a href="#treatmentPlan" class="tab-link active button">治疗方案</a>
                <%--<a href="#screeningStandard" class="tab-link button">初筛要点</a>--%>
                <a href="#entryCriteria" class="tab-link button">入排标准</a>
                <a href="#researchCenterInfo" class="tab-link button">研究中心</a>
                <a href="#patientRights" class="tab-link button">患者权益</a>
            </div>
            <div class="content-block" style="padding: 0; margin: 0;">
                <div class="tabs">
                    <div id="treatmentPlan" class="tab active">
                        <div class="content-block" style="padding: 0; margin: 0">
                            <p>${applicationVO.recruitmentVO.treatmentPlan}</p>
                        </div>
                    </div>
                    <div id="screeningStandard" class="tab">
                        <div class="content-block" style="padding: 0; margin: 0">
                            <p>${applicationVO.recruitmentVO.screeningStandard}</p>
                        </div>
                    </div>
                    <div id="entryCriteria" class="tab">
                        <div class="content-block" style="padding: 0; margin: 0">
                            <p>${applicationVO.recruitmentVO.entryCriteria}</p>
                        </div>
                    </div>
                    <div id="researchCenterInfo" class="tab">
                        <div class="content-block" style="padding: 0; margin: 0">
                            <div class="list-block">
                                <ul>
                                    <c:forEach items="${applicationVO.recruitmentVO.researchCenterVOList}" var="item">
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
                            <p>${applicationVO.recruitmentVO.patientRights}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type='text/javascript' src='/static/js/lib/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/util/ajax.js?_v=${version}' charset="utf-8"></script>
<script type='text/javascript' src='/static/js/recruitment/application/detail.js?_v=${version}'
        charset="utf-8"></script>
</html>