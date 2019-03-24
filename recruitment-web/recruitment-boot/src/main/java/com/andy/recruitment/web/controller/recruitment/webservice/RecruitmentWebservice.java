package com.andy.recruitment.web.controller.recruitment.webservice;

import com.andy.recruitment.recruitment.ao.RecruitmentAO;
import com.andy.recruitment.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.researchcenter.ao.ResearchCenterAO;
import com.andy.recruitment.researchcenter.model.ResearchCenterInfo;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentAddRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentUpdateRQ;
import com.andy.recruitment.web.controller.recruitment.util.RecruitmentUtil;
import com.xgimi.auth.Login;
import com.xgimi.context.ServletContext;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 招募信息webservice接口
 *
 * @author 庞先海 2019-01-06
 */
@RestController
@RequestMapping("/recruitment")
public class RecruitmentWebservice {

    private final RecruitmentAO recruitmentAO;

    private final RegionAO regionAO;

    private final ResearchCenterAO researchCenterAO;

    @Autowired
    public RecruitmentWebservice(RecruitmentAO recruitmentAO, RegionAO regionAO, ResearchCenterAO researchCenterAO) {
        this.recruitmentAO = recruitmentAO;
        this.regionAO = regionAO;
        this.researchCenterAO = researchCenterAO;
    }

    @Login
    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public boolean addRecruitment(@RequestBody RecruitmentAddRQ recruitmentAddRQ) {
        RecruitmentInfo recruitmentInfo = RecruitmentUtil.transformRecruitmentInfo(recruitmentAddRQ);
        Long recruitmentId = recruitmentAO.addRecruitmentInfo(recruitmentInfo, ServletContext.getLoginUname());
        List<ResearchCenterInfo> centerInfoList = RecruitmentUtil.transformResearchCenterInfo(
            recruitmentAddRQ.getResearchCenterList());
        this.researchCenterAO.addResearchCenter(recruitmentId, centerInfoList, ServletContext.getLoginUname());
        return true;
    }

    @Login
    @RequestMapping(value = "/{recruitmentId:\\d+}/begin.json", method = RequestMethod.POST)
    public boolean recruitmentBegin(@PathVariable Long recruitmentId) {
        RecruitmentInfo recruitmentInfo = new RecruitmentInfo();
        recruitmentInfo.setRecruitmentId(recruitmentId);
        recruitmentInfo.setStatus(RecruitmentStatus.IN_PROCESS);
        this.recruitmentAO.updateRecruitmentInfo(recruitmentInfo, ServletContext.getLoginUname());
        return true;
    }

    @Login
    @RequestMapping(value = "/{recruitmentId:\\d+}/end.json", method = RequestMethod.POST)
    public boolean recruitmentEnd(@PathVariable Long recruitmentId) {
        RecruitmentInfo recruitmentInfo = new RecruitmentInfo();
        recruitmentInfo.setRecruitmentId(recruitmentId);
        recruitmentInfo.setStatus(RecruitmentStatus.FINISHED);
        this.recruitmentAO.updateRecruitmentInfo(recruitmentInfo, ServletContext.getLoginUname());
        return true;
    }

    @Login
    @RequestMapping(value = "/update.json", method = RequestMethod.POST)
    public boolean updateRecruitment(@RequestBody RecruitmentUpdateRQ recruitmentUpdateRQ) {
        RecruitmentInfo recruitmentInfo = RecruitmentUtil.transformRecruitmentInfo(recruitmentUpdateRQ);
        recruitmentAO.updateRecruitmentInfo(recruitmentInfo, ServletContext.getLoginUname());
        return true;
    }
}
