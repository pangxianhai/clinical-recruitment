package com.andy.recruitment.web.controller.researcher.controller;

import com.andy.recruitment.biz.researcher.service.ResearcherService;
import com.andy.recruitment.dao.researcher.constant.ResearcherStatus;
import com.andy.recruitment.dao.researcher.entity.ResearcherInfoDO;
import com.andy.recruitment.dao.researcher.entity.ResearcherQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.researcher.request.ResearcherAddReq;
import com.andy.recruitment.web.controller.researcher.request.ResearcherQueryReq;
import com.andy.recruitment.web.controller.researcher.response.ResearcherInfoRes;
import com.andy.recruitment.web.controller.researcher.util.ResearcherUtil;
import com.soyoung.base.auth.LoginInfo;
import com.soyoung.base.auth.RoleType;
import com.soyoung.base.context.ServletContext;
import com.soyoung.base.converter.MyParameter;
import com.soyoung.base.page.PageResult;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        ResearcherQuery query = ResearcherUtil.transformResearcherQuery(queryReq);
        PageResult<ResearcherInfoDO> pageResult = this.researcherService.getResearcherInfo(query,
            queryReq.getPaginator());
        List<ResearcherInfoRes> researcherInfoResList = ResearcherUtil.transformResearcherInfoRes(pageResult.getData());
        return new PageResult<>(researcherInfoResList, pageResult.getPaginator());
    }

    @RequiresUser
    @GetMapping("/{researcherId:\\d+}")
    public ResearcherInfoRes getResearcherDetail(@PathVariable Long researcherId) {
        ResearcherInfoDO researcherInfoDo = this.researcherService.getResearcherInfoByResearchId(researcherId);
        return ResearcherUtil.transformResearcherInfoRes(researcherInfoDo);
    }

    @RequiresUser
    @PostMapping
    public boolean addResearcher(@RequestBody ResearcherAddReq researcherAddReq) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        ResearcherInfoDO researcherInfoDo = ResearcherUtil.transformResearcherInfo(researcherAddReq);
        if (RoleType.MANAGER.equals(loginInfo.getRoleType())) {
            researcherInfoDo.setStatus(ResearcherStatus.NORMAL);
        } else {
            researcherInfoDo.setStatus(ResearcherStatus.NON_EXAMINE);
        }
        UserInfoDO userInfoDo = ResearcherUtil.transformUserInfo(researcherAddReq);
        this.researcherService.registerResearcher(researcherInfoDo, userInfoDo, loginInfo.getRealName());
        return true;
    }

    @RequiresUser
    @PutMapping("/{researcherId:\\d+}")
    public boolean updateResearcher(@PathVariable Long researcherId, @RequestBody ResearcherAddReq researcherAddReq) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        ResearcherInfoDO researcherInfoDo = ResearcherUtil.transformResearcherInfo(researcherAddReq);
        researcherInfoDo.setId(researcherId);
        //所属科室暂不允许修改
        researcherInfoDo.setOrganizationId(null);
        researcherInfoDo.setDepartmentId(null);
        UserInfoDO userInfoDo = ResearcherUtil.transformUserInfo(researcherAddReq);
        this.researcherService.updateResearcher(researcherInfoDo, userInfoDo, loginInfo.getRealName());
        return true;
    }
}
