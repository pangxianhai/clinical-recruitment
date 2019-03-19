package com.andy.recruitment.web.controller.recruitment.webservice;

import com.andy.recruitment.recruitment.ao.RecruitmentAO;
import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.recruitment.model.RecruitmentQueryParam;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.region.model.AddressInfo;
import com.andy.recruitment.researchcenter.ao.ResearchCenterAO;
import com.andy.recruitment.researchcenter.model.ResearchCenterInfo;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentQueryRQ;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentVO;
import com.andy.recruitment.web.controller.recruitment.util.RecruitmentUtil;
import com.andy.recruitment.web.controller.region.response.RegionVO;
import com.andy.recruitment.web.controller.region.util.RegionUtil;
import com.xgimi.auth.Login;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.util.StringUtil;
import com.xgimi.logger.log4j.Logger;
import com.xgimi.logger.log4j.MyLogger;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 招募信息controller
 *
 * @author 庞先海 2019-01-02
 */
@Controller
@RequestMapping("/recruitment")
public class RecruitmentController {

    @Logger
    private MyLogger logger;

    private final RecruitmentAO recruitmentAO;

    private final RegionAO regionAO;

    private final ResearchCenterAO researchCenterAO;

    @Value("${indication.list}")
    private String indicationConfig;

    @Autowired
    public RecruitmentController(RecruitmentAO recruitmentAO, RegionAO regionAO, ResearchCenterAO researchCenterAO) {
        this.recruitmentAO = recruitmentAO;
        this.regionAO = regionAO;
        this.researchCenterAO = researchCenterAO;
    }

    @RequestMapping("/add-pc")
    public String addRecruitment(Map<String, Object> model) {
        List<RegionVO> regionVOList = RegionUtil.buildSelectOptions(null, regionAO);
        model.put("regionVOList", regionVOList);
        return "recruitment/add-pc";
    }

    @RequestMapping(value = "/detail/{recruitmentId:\\d+}", method = RequestMethod.GET)
    public String recruitmentDetail(@PathVariable Long recruitmentId, Long doctorId, Map<String, Object> model) {
        RecruitmentInfo recruitmentInfo = recruitmentAO.getRecruitmentInfoById(recruitmentId);
        RecruitmentVO recruitmentVO = RecruitmentUtil.transformRecruitmentVO(recruitmentInfo);
        List<ResearchCenterInfo> researchCenterInfoList = this.researchCenterAO.getResearchCenterByRecruitmentId(
            recruitmentId);
        model.put("researchCenterListVO", RecruitmentUtil.transformResearchCenterVO(regionAO, researchCenterInfoList));
        model.put("recruitmentInfo", recruitmentVO);
        model.put("doctorId", doctorId);
        return "recruitment/detail";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String recruitmentList(Map<String, Object> model) {
        model.put("indicationOptions", this.indicationConfig);
        return "recruitment/list";
    }

    @RequestMapping(value = "/listInfo", method = RequestMethod.GET)
    public String recruitmentListResult(RecruitmentQueryRQ recruitmentQueryRQ, Map<String, Object> model) {
        PageResult<RecruitmentInfo> pageResult = this.queryRecruitmentInfo(recruitmentQueryRQ);
        model.put("recruitmentInfoList", pageResult.getData());
        return "recruitment/listInfo";
    }

    @Login
    @RequestMapping(value = "/list-pc", method = RequestMethod.GET)
    public String recruitmentListPc(Map<String, Object> model) {
        model.put("indicationOptions", this.indicationConfig);
        return "recruitment/list-pc";
    }

    @Login
    @RequestMapping(value = "/listPcInfo", method = RequestMethod.GET)
    public String recruitmentListPcResult(RecruitmentQueryRQ recruitmentQueryRQ, Map<String, Object> model) {
        PageResult<RecruitmentInfo> pageResult = this.queryRecruitmentInfo(recruitmentQueryRQ);
        List<RecruitmentVO> recruitmentVOList = RecruitmentUtil.transformRecruitmentVO(pageResult.getData());
        model.put("recruitmentInfoList", recruitmentVOList);
        model.put("paginator", pageResult.getPaginator());
        return "recruitment/listInfo-pc";
    }

    @Login
    @RequestMapping(value = "/detailPc/{recruitmentId:\\d+}", method = RequestMethod.GET)
    public String recruitmentListPcDetail(@PathVariable Long recruitmentId, Map<String, Object> model) {
        RecruitmentInfo recruitmentInfo = this.recruitmentAO.getRecruitmentInfoById(recruitmentId);
        RecruitmentVO recruitmentVO = RecruitmentUtil.transformRecruitmentVO(recruitmentInfo);
        List<ResearchCenterInfo> researchCenterInfoList = this.researchCenterAO.getResearchCenterByRecruitmentId(
            recruitmentId);
        model.put("researchCenterListVO", RecruitmentUtil.transformResearchCenterVO(regionAO, researchCenterInfoList));
        model.put("recruitmentVO", recruitmentVO);
        return "recruitment/detail-pc";
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
