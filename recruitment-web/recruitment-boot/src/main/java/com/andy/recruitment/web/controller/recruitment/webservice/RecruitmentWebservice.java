package com.andy.recruitment.web.controller.recruitment.webservice;

import com.andy.recruitment.recruitment.ao.RecruitmentAO;
import com.andy.recruitment.recruitment.model.RecruitmentInfo;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentAddRQ;
import com.andy.recruitment.web.controller.recruitment.util.RecruitmentUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public RecruitmentWebservice(RecruitmentAO recruitmentAO) {
        this.recruitmentAO = recruitmentAO;
    }

    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public boolean addRecruitment(@RequestBody RecruitmentAddRQ recruitmentAddRQ) {
        RecruitmentInfo recruitmentInfo = RecruitmentUtil.transformRecruitmentInfo(recruitmentAddRQ);
        recruitmentAO.addRecruitmentInfo(recruitmentInfo, "aaaa");
        return true;
    }
}
