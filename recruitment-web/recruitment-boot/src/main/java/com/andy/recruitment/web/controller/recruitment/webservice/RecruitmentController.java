package com.andy.recruitment.web.controller.recruitment.webservice;

import com.andy.recruitment.recruitment.ao.RecruitmentAO;
import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.recruitment.model.RecruitmentQueryParam;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentVO;
import com.andy.recruitment.web.controller.recruitment.util.RecruitmentUtil;
import com.andy.recruitment.web.controller.region.util.RegionUtil;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;
import com.xgimi.commons.util.JsonUtil;
import com.xgimi.commons.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Value("${indication.list}")
    private String indicationConfig;

    @Autowired
    public RecruitmentController(RecruitmentAO recruitmentAO, RegionAO regionAO) {
        this.recruitmentAO = recruitmentAO;
        this.regionAO = regionAO;
    }


    @RequestMapping("/add")
    public String addRecruitment() {
        return "recruitment/add";
    }

    @RequestMapping(value = "/detail/{recruitmentId:\\d+}", method = RequestMethod.GET)
    public String recruitmentDetail(@PathVariable Long recruitmentId, Map<String, Object> model) {
        RecruitmentInfo recruitmentInfo = recruitmentAO.getRecruitmentInfoById(recruitmentId);
        RecruitmentVO recruitmentVO = RecruitmentUtil.transformRecruitmentVO(recruitmentInfo);
        model.put("recruitmentInfo", recruitmentVO);
        return "recruitment/detail";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String recruitmentList(Map<String, Object> model) {
        RecruitmentQueryParam queryParam = new RecruitmentQueryParam();
        PageResult<RecruitmentInfo> pageResult = recruitmentAO.getRecruitmentInfo(queryParam, new Paginator(1, 5));
        model.put("recruitmentInfoList", pageResult.getData());
        model.put("paginator", pageResult.getPaginator());
        model.put("regionVOList", RegionUtil.buildSelectOptions(regionAO));
        model.put("indicationOptions", JsonUtil.toJson(this.parseIndicationList()));
        return "recruitment/list";
    }

    private List<Map<String, String>> parseIndicationList() {
        List<Map<String, String>> indicationList = new ArrayList<>();
        Map<String, String> allIndication = new HashMap<>(2);
        allIndication.put("label", "所有疾病类型");
        allIndication.put("value", "");
        indicationList.add(allIndication);
        if (StringUtil.isEmpty(indicationConfig)) {
            return indicationList;
        }
        for (String indication : indicationConfig.split(",")) {
            Map<String, String> indicationMap = new HashMap<>(2);
            indicationMap.put("label", indication);
            indicationMap.put("value", indication);
            indicationList.add(indicationMap);
        }
        return indicationList;
    }
}
