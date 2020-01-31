package com.andy.recruitment.web.controller.recruitment.controller;

import com.andy.recruitment.biz.recruitment.service.RecruitmentService;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentInfoDO;
import com.andy.recruitment.dao.recruitment.entity.RecruitmentQuery;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentAddReq;
import com.andy.recruitment.web.controller.recruitment.request.RecruitmentQueryReq;
import com.andy.recruitment.web.controller.recruitment.util.RecruitmentUtil;
import com.soyoung.base.auth.RoleType;
import com.soyoung.base.context.ServletContext;
import com.soyoung.base.converter.MyParameter;
import com.soyoung.base.page.PageResult;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 招募项目controller接口
 *
 * @author 庞先海 2020-01-30
 */
@RestController
@RequestMapping("/recruitment")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @Autowired
    public RecruitmentController(RecruitmentService recruitmentService) {
        this.recruitmentService = recruitmentService;
    }

    @GetMapping
    public PageResult<RecruitmentInfoDO> listRecruitment(@MyParameter RecruitmentQueryReq queryReq) {
        RecruitmentQuery recruitmentQuery = RecruitmentUtil.transformQueryParam(queryReq);
        return this.recruitmentService.getRecruitmentInfo(recruitmentQuery, queryReq.getPaginator());
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PostMapping
    public boolean addRecruitment(@RequestBody RecruitmentAddReq addReq) {
        RecruitmentInfoDO recruitmentInfoDo = RecruitmentUtil.transformRecruitmentInfoDo(addReq);
        this.recruitmentService.addRecruitmentInfo(recruitmentInfoDo, addReq.getOrganizationList(),
            ServletContext.getLoginInfo().getRealName());
        return true;
    }

}
