package com.andy.recruitment.web.controller.recruitment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 招募信息
 *
 * @author 庞先海 2019-01-02
 */
@Controller
@RequestMapping("/recruitment")
public class RecruitmentController {

    @RequestMapping("/add")
    public String addRecruitment() {
        return "recruitment/add";
    }
}
