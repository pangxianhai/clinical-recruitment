package com.andy.recruitment.web.controller.recruitment.webservice;

import com.andy.recruitment.patient.PatientAO;
import com.andy.recruitment.recruitment.ao.RecruitmentApplicationAO;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationInfo;
import com.andy.recruitment.recruitment.model.RecruitmentApplicationQueryParam;
import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationQueryParamRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentApplicationUpdateRQ;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentApplicationVO;
import com.andy.recruitment.web.controller.recruitment.util.RecruitmentUtil;
import com.xgimi.auth.Login;
import com.xgimi.auth.LoginInfo;
import com.xgimi.commons.page.PageResult;
import com.xgimi.context.ServletContext;
import com.xgimi.converter.MyParameter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 招募信息申请记录webservice接口
 *
 * @author 庞先海 2019-01-25
 */
@RestController
@RequestMapping("/recruitmentapplication")
public class RecruitmentApplicationWebservice {

    private final RecruitmentApplicationAO recruitmentApplicationAO;

    private final PatientAO patientAO;

    private final UserAO userAO;

    @Autowired
    public RecruitmentApplicationWebservice(RecruitmentApplicationAO recruitmentApplicationAO, PatientAO patientAO,
                                            UserAO userAO) {
        this.recruitmentApplicationAO = recruitmentApplicationAO;
        this.patientAO = patientAO;
        this.userAO = userAO;
    }

    @Login
    @RequestMapping(value = "", method = RequestMethod.POST)
    public boolean applicationRecruitment(@RequestBody RecruitmentApplicationRQ applicationRQ) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        RecruitmentApplicationInfo applicationInfo = RecruitmentUtil.transformApplicationInfo(applicationRQ);
        this.recruitmentApplicationAO.addRecruitmentApplication(loginInfo.getUserId(), applicationInfo,
                                                                ServletContext.getLoginUname());
        return true;
    }

    @Login
    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageResult<RecruitmentApplicationVO> getApplicationInfo(
        @MyParameter RecruitmentApplicationQueryParamRQ queryParamRQ) {
        RecruitmentApplicationQueryParam queryParam = RecruitmentUtil.transformApplicationQueryParam(queryParamRQ);
        PageResult<RecruitmentApplicationInfo> pageResult = this.recruitmentApplicationAO.getRecruitmentApplicationInfo(
            queryParam, queryParamRQ.getPaginator());
        List<RecruitmentApplicationVO> applicationVOList = RecruitmentUtil.transformApplicationVO(pageResult.getData());
        return new PageResult<>(applicationVOList, pageResult.getPaginator());
    }

    @Login
    @RequestMapping(value = "/{applicationId:\\d+}", method = RequestMethod.PUT)
    public boolean updateApplicationInfo(@PathVariable Long applicationId,
                                         @RequestBody RecruitmentApplicationUpdateRQ updateRQ) {
        RecruitmentApplicationInfo applicationInfo = RecruitmentUtil.transformApplicationInfo(updateRQ);
        applicationInfo.setApplicationId(applicationId);
        this.recruitmentApplicationAO.updateRecruitmentApplication(applicationInfo, ServletContext.getLoginUname());
        return true;
    }
}
