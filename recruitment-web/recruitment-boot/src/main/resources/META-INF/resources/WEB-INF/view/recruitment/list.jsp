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
        <jsp:include page="../components/footer.jsp">
            <jsp:param value="recruitmentList" name="menuItem"/>
        </jsp:include>
        <div class="content">
            <form id="searchForm">
                <div class="searchbar row">
                    <div class="search-input col-85">
                        <input type="search" name="queryText" id='search'
                               placeholder='输入招募项目、登记编号、适应症状进行搜索'/>
                    </div>
                    <a href="javascript:void(0)" id="searchButton"
                       class="button button-fill button-primary col-15">
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
                                   placeholder="所有疾病类型"
                                   name="indication"
                                   id='indicationPicker'/>
                        </div>
                        <div class="col-33">
                            <input style="width: 100%;font-size: 14px;"
                                   type="text"
                                   placeholder="选择地区"
                                   name="addressText"
                                   id='addressPicker'/>
                        </div>
                    </div>
                </div>
            </form>
            <div id="recruitment-list" class="recruitment-list"></div>
        </div>
    </div>
    <div class="popup popup-qrcode">
        <a href="#" class="close-popup">
            <div class="content-block" id="qrCode"></div>
        </a>
    </div>
</div>
<input type="hidden" id="indicationOptions" value="${indicationOptions}">
<script type='text/javascript' src='/static/js/lib/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/sm-city-picker.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/lib/qrcode.min.js' charset='utf-8'></script>
<script type='text/javascript' src='/static/js/util/ajax.js?_v=${version}' charset="utf-8"></script>
<script type='text/javascript' src='/static/js/recruitment/list.js?_v=${version}'
        charset="utf-8"></script>
</body>
</html>