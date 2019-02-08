<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${applicationVOList}" var="application">
    <div class="card recruitmentInfo-card">
        <div class="card-header">${application.recruitmentVO.title}</div>
        <div class="card-content">
            <div class="content-padded ">
                <div class="row">
                    <div class="col-50">
                        <div class="row">
                            <div class="col-label">患者名称</div>
                            <div class="col-content">${application.patientVO.userInfoVO.realName}</div>
                        </div>
                    </div>
                    <div class="col-50">
                        <div class="row">
                            <div class="col-label">状态</div>
                            <div class="col-content">${application.status.desc}</div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-50">
                        <div class="row">
                            <div class="col-label">报名时间</div>
                            <div class="col-content">${application.applicationTime}</div>
                        </div>
                    </div>
                    <div class="col-50">
                        <div class="row">
                            <div class="col-label">推荐医生</div>
                            <div class="col-content">${application.doctorInfoVO.userInfoVO.realName}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:forEach>