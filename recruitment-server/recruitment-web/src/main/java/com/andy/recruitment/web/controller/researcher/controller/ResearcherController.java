package com.andy.recruitment.web.controller.researcher.controller;

import com.andy.recruitment.biz.researcher.service.ResearcherService;
import com.andy.recruitment.dao.researcher.entity.ResearcherInfoDO;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.researcher.request.ResearcherAddReq;
import com.andy.recruitment.web.controller.researcher.request.ResearcherQueryReq;
import com.andy.recruitment.web.controller.researcher.response.ResearcherInfoRes;
import com.andy.recruitment.web.controller.researcher.util.ResearcherUtil;
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
 * 研究员 controller 接口
 *
 * @author 庞先海 2020-01-31
 */
@RestController
@RequestMapping("/researcher")
public class ResearcherController {

    private final ResearcherService researcherService;

    @Autowired
    public ResearcherController(ResearcherService researcherService) {
        this.researcherService = researcherService;
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @GetMapping
    public PageResult<ResearcherInfoRes> listResearcher(@MyParameter ResearcherQueryReq queryReq) {
        return new PageResult<>(null, null);
    }

    @RequiresUser
    @PostMapping
    public boolean addResearcher(@RequestBody ResearcherAddReq researcherAddReq) {
        ResearcherInfoDO researcherInfoDo = ResearcherUtil.transformResearcherInfo(researcherAddReq);
        UserInfoDO userInfoDo = ResearcherUtil.transformUserInfo(researcherAddReq);
        this.researcherService.registerResearcher(researcherInfoDo, userInfoDo,
            ServletContext.getLoginInfo().getRealName());
        return true;
    }
}
