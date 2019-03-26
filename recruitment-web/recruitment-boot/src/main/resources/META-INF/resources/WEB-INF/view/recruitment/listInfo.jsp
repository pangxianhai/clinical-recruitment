<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                                <div class="col-label">招募人数</div>
                                <div class="col-content">${recruitmentInfo.recruitmentNumber}人</div>
                            </div>
                        </div>
                        <div class="col-50">
                            <div class="row">
                                <div class="col-label">招募状态</div>
                                <div class="col-content">${recruitmentInfo.status.desc}</div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-label">药物名称</div>
                        <div class="col-content">${recruitmentInfo.drugName}</div>
                    </div>
                    <div class="row">
                        <div class="col-label">适应症状</div>
                        <div class="col-content">${recruitmentInfo.indication}</div>
                    </div>
                </div>
                <c:if test="${userInfo.userType.code == 2}">
                    <div class="sign-up-panel">
                        <a href="javascript:void(0)"
                           item="recommend_qrcode"
                           class="button button-fill button-warning"
                           style="width: 30%"
                           recruitmentId="${recruitmentInfo.recruitmentId}"
                        >推荐二维码</a>
                    </div>
                </c:if>
                <c:if test="${empty userInfo || userInfo.userType.code == 3}">
                    <div class="sign-up-panel">
                        <a href="javascript:void(0)"
                           item="sign_up_button"
                           class="button button-fill button-warning external"
                           recruitmentId="${recruitmentInfo.recruitmentId}"
                        >我要参加</a>
                    </div>
                </c:if>
            </div>
        </div>
    </a>
</c:forEach>