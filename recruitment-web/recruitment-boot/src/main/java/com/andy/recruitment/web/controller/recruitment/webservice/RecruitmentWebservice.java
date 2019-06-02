package com.andy.recruitment.web.controller.recruitment.webservice;

import com.andy.recruitment.recruitment.ao.RecruitmentAO;
import com.andy.recruitment.recruitment.constant.RecruitmentStatus;
import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.recruitment.model.RecruitmentQueryParam;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.region.model.AddressInfo;
import com.andy.recruitment.researchcenter.ao.ResearchCenterAO;
import com.andy.recruitment.researchcenter.model.ResearchCenterInfo;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentAddRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentQueryRQ;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentUpdateRQ;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentVO;
import com.andy.recruitment.web.controller.recruitment.response.ResearchCenterVO;
import com.andy.recruitment.web.controller.recruitment.util.RecruitmentUtil;
import com.xgimi.auth.Login;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.commons.util.StringUtil;
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
    @RequestMapping(value = "", method = RequestMethod.POST)
    public boolean addRecruitment(@RequestBody RecruitmentAddRQ recruitmentAddRQ) {
        RecruitmentInfo recruitmentInfo = RecruitmentUtil.transformRecruitmentInfo(recruitmentAddRQ);
        Long recruitmentId = recruitmentAO.addRecruitmentInfo(recruitmentInfo, ServletContext.getLoginUname());
        List<ResearchCenterInfo> centerInfoList = RecruitmentUtil.transformResearchCenterInfo(
            recruitmentAddRQ.getResearchCenterList());
        this.researchCenterAO.addResearchCenter(recruitmentId, centerInfoList, ServletContext.getLoginUname());
        return true;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageResult<RecruitmentVO> recruitmentListPcResult(@MyParameter RecruitmentQueryRQ recruitmentQueryRQ) {
        PageResult<RecruitmentInfo> pageResult = this.queryRecruitmentInfo(recruitmentQueryRQ);
        List<RecruitmentVO> recruitmentVOList = RecruitmentUtil.transformRecruitmentVO(pageResult.getData());
        return new PageResult<>(recruitmentVOList, pageResult.getPaginator());
    }

    @Login
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public boolean updateRecruitment(@RequestBody RecruitmentUpdateRQ recruitmentUpdateRQ) {
        RecruitmentInfo recruitmentInfo = RecruitmentUtil.transformRecruitmentInfo(recruitmentUpdateRQ);
        List<ResearchCenterInfo> researchCenterInfoList = RecruitmentUtil.transformResearchCenterInfoByUpdate(
            recruitmentUpdateRQ.getResearchCenterList());
        recruitmentAO.updateRecruitmentInfo(recruitmentInfo, ServletContext.getLoginUname());
        if (CollectionUtil.isEmpty(researchCenterInfoList)) {
            return true;
        }
        this.researchCenterAO.updateResearchCenter(recruitmentUpdateRQ.getRecruitmentId(), researchCenterInfoList,
                                                   ServletContext.getLoginUname());
        return true;
    }

    @RequestMapping(value = "/{recruitmentId:\\d+}/begin", method = RequestMethod.POST)
    public boolean recruitmentBegin(@PathVariable Long recruitmentId) {
        RecruitmentInfo recruitmentInfo = new RecruitmentInfo();
        recruitmentInfo.setRecruitmentId(recruitmentId);
        recruitmentInfo.setStatus(RecruitmentStatus.IN_PROCESS);
        this.recruitmentAO.updateRecruitmentInfo(recruitmentInfo, ServletContext.getLoginUname());
        return true;
    }

    @RequestMapping(value = "/{recruitmentId:\\d+}/end", method = RequestMethod.POST)
    public boolean recruitmentEnd(@PathVariable Long recruitmentId) {
        RecruitmentInfo recruitmentInfo = new RecruitmentInfo();
        recruitmentInfo.setRecruitmentId(recruitmentId);
        recruitmentInfo.setStatus(RecruitmentStatus.FINISHED);
        this.recruitmentAO.updateRecruitmentInfo(recruitmentInfo, ServletContext.getLoginUname());
        return true;
    }

    @RequestMapping(value = "/{recruitmentId:\\d+}", method = RequestMethod.GET)
    public RecruitmentVO getRecruitment(@PathVariable Long recruitmentId) {
        RecruitmentInfo recruitmentInfo = this.recruitmentAO.getRecruitmentInfoById(recruitmentId);
        return RecruitmentUtil.transformRecruitmentVO(recruitmentInfo);
    }

    @RequestMapping(value = "/{recruitmentId:\\d+}/center", method = RequestMethod.GET)
    public List<ResearchCenterVO> getRecruitmentCenter(@PathVariable Long recruitmentId) {
        List<ResearchCenterInfo> researchCenterInfoList = this.researchCenterAO.getResearchCenterByRecruitmentId(
            recruitmentId);
        return RecruitmentUtil.transformResearchCenterVO(regionAO, researchCenterInfoList);
    }


    private PageResult<RecruitmentInfo> queryRecruitmentInfo(RecruitmentQueryRQ queryRQ) {
        RecruitmentQueryParam queryParam = RecruitmentUtil.transformQueryParam(queryRQ);
        queryParam.setIndication(queryRQ.getIndication());
        queryParam.setQueryText(queryRQ.getQueryText());
        if (StringUtil.isNotEmpty(queryRQ.getAddressText())) {
            AddressInfo addressInfo = regionAO.parseAddressInfo(queryRQ.getAddressText());
            if (null != addressInfo) {
                if (null != addressInfo.getProvince()) {
                    queryParam.setProvinceId(addressInfo.getProvince().getRegionId());
                }
                if (null != addressInfo.getCity()) {
                    queryParam.setCityId(addressInfo.getCity().getRegionId());
                }
                if (null != addressInfo.getDistrict()) {
                    queryParam.setDistrictId(addressInfo.getDistrict().getRegionId());
                }
            }
        }
        return recruitmentAO.getRecruitmentInfo(queryParam, queryRQ.getPaginator());
    }
}
