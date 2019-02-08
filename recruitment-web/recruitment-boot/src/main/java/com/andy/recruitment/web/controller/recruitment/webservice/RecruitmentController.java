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
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;
import com.xgimi.commons.util.StringUtil;
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


    @RequestMapping("/add")
    public String addRecruitment() {
        return "recruitment/add";
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
        RecruitmentQueryParam queryParam = new RecruitmentQueryParam();
        queryParam.setIndication(recruitmentQueryRQ.getIndication());
        queryParam.setQueryText(recruitmentQueryRQ.getQueryText());
        if (StringUtil.isNotEmpty(recruitmentQueryRQ.getAddressText())) {
            AddressInfo addressInfo = regionAO.parseAddressInfo(recruitmentQueryRQ.getAddressText());
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
        PageResult<RecruitmentInfo> pageResult = recruitmentAO.getRecruitmentInfo(queryParam, new Paginator(1, 5));
        model.put("recruitmentInfoList", pageResult.getData());
        return "recruitment/listInfo";
    }
}
