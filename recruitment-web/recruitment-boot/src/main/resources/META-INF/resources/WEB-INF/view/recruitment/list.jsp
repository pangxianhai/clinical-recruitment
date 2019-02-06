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
    <link rel="stylesheet" href="/static/css/recruitment/list.css?_v=${version}">
</head>
<body>
<div class="page-group">
    <div class="page page-current">
        <header class="bar bar-nav">
            <c:if test="${userInfo.userType.code == 1}">
                <a class="button pull-right" href="/recruitment/add">
                    发布任务
                </a>
            </c:if>

            <h1 class="title">任务大厅</h1>
        </header>
        <div class="content">
            <div class="searchbar row">
                <div class="search-input col-85">
                    <input type="search" id='search' placeholder='输入招募项目、登记编号、适应症状进行搜索'/>
                </div>
                <a class="button button-fill button-primary col-15">
                    <span class="icon icon-search"></span>
                </a>
            </div>
            <div class="content-padded">
                <div class="row">
                    <div class="col-33">
                        <p style="margin: 0">智能推荐</p>
                    </div>
                    <div class="col-33">
                        <input style="width: 100%;font-size: 14px;"
                               type="text"
                               value="所有疾病类型"
                               id='indicationPicker'/>
                    </div>
                    <div class="col-33">
                        <input style="width: 100%;font-size: 14px;"
                               type="text"
                               value="所有类型"
                               id='addressPicker'/>
                    </div>
                </div>
            </div>
            <div class="recruitment-list">
                <c:forEach items="${recruitmentInfoList}" var="recruitmentInfo">
                    <a href="/recruitment/detail/${recruitmentInfo.recruitmentId}" external>
                        <div class="card recruitmentInfo-card">
                            <div class="card-header">${recruitmentInfo.title}</div>
                            <div class="card-content">
                                <div class="content-padded ">
                                    <div class="row">
                                        <div class="col-50">
                                            <div class="row">
                                                <div class="col-label">登记编号</div>
                                                <div class="col-content">${recruitmentInfo.registerCode}</div>
                                            </div>
                                        </div>
                                        <div class="col-50">
                                            <div class="row">
                                                <div class="col-label">实验分期</div>
                                                <div class="col-content">${recruitmentInfo.practiceStages}</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-50">
                                            <div class="row">
                                                <div class="col-label">药物名称</div>
                                                <div class="col-content">${recruitmentInfo.drugName}</div>
                                            </div>
                                        </div>
                                        <div class="col-50">
                                            <div class="row">
                                                <div class="col-label">招募人数</div>
                                                <div class="col-content">${recruitmentInfo.recruitmentNumber}人</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-50">
                                            <div class="row">
                                                <div class="col-label">招募状态</div>
                                                <div class="col-content">${recruitmentInfo.status.desc}</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-label">适应症状</div>
                                        <div class="col-content">${recruitmentInfo.indication}</div>
                                    </div>
                                </div>
                                <c:if test="${userInfo.userType.code == 3}">
                                    <div class="sign-up-panel">
                                        <a href="javascript:void(0)"
                                           item="sign_up_button"
                                           class="button button-fill button-warning"
                                           recruitmentId="${recruitmentInfo.recruitmentId}"
                                        >报名</a>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
        <jsp:include page="../components/footer.jsp">
            <jsp:param value="recruitmentList" name="menuItem"/>
        </jsp:include>
    </div>
</div>
<script type='text/javascript' src='/static/js/lib/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/util/ajax.js?_v=${version}' charset="utf-8"></script>
<script type='text/javascript' src='/static/js/recruitment/list.js?_v=${version}'
        charset="utf-8"></script>
</body>
</html>