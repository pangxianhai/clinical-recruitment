package com.andy.recruitment.web.controller.recruitment.webservice;

import com.andy.recruitment.recruitment.ao.RecruitmentAO;
import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.web.controller.recruitment.response.RecruitmentVO;
import com.andy.recruitment.web.controller.recruitment.util.RecruitmentUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 招募信息controller
 *
 * @author 庞先海 2019-01-02
 */
@Controller
@RequestMapping("/recruitment")
public class RecruitmentController {

    private final RecruitmentAO recruitmentAO;

    @Autowired
    public RecruitmentController(RecruitmentAO recruitmentAO) {
        this.recruitmentAO = recruitmentAO;
    }


    @RequestMapping("/add")
    public String addRecruitment() {
        return "recruitment/add";
    }

    @RequestMapping("/detail/{recruitmentId:\\d+}")
    public String recruitmentDetail(@PathVariable Long recruitmentId, Map<String, Object> model) {
        RecruitmentInfo recruitmentInfo = recruitmentAO.getRecruitmentInfoById(recruitmentId);
        RecruitmentVO recruitmentVO = RecruitmentUtil.transformRecruitmentVO(recruitmentInfo);
        model.put("recruitmentInfo", recruitmentVO);
        return "recruitment/detail";
    }
}
