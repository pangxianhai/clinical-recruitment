package com.andy.recruitment.web.controller.reference.controller;

import com.andy.recruitment.api.reference.request.ReferenceAddReq;
import com.andy.recruitment.api.reference.request.ReferenceQueryReq;
import com.andy.recruitment.api.reference.request.ReferenceRegisterReq;
import com.andy.recruitment.api.reference.response.ReferenceDetailInfoRes;
import com.andy.recruitment.biz.reference.service.ReferenceService;
import com.andy.recruitment.biz.user.service.UserService;
import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.common.reference.constant.ReferenceRole;
import com.andy.recruitment.common.reference.constant.ReferenceStatus;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.reference.util.ReferenceUtil;
import com.andy.spring.auth.LoginInfo;
import com.andy.spring.auth.RoleType;
import com.andy.spring.context.ServletContext;
import com.andy.spring.converter.MyParameter;
import com.andy.spring.page.PageResult;
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
 * 推荐人 controller 接口
 *
 * @author 庞先海 2020-02-15
 */
@RestController
@RequestMapping("/reference")
public class ReferenceController {

    private final ReferenceService referenceService;

    private final UserService userService;

    @Autowired
    public ReferenceController(ReferenceService referenceService, UserService userService) {
        this.referenceService = referenceService;
        this.userService = userService;
    }

    @PostMapping("register")
    public LoginInfo registerReference(@RequestBody ReferenceRegisterReq referenceRegisterReq) {
        ReferenceInfoDO referenceInfoDo = ReferenceUtil.transformReferenceInfoDo(referenceRegisterReq);
        if (ReferenceRole.HEADS_OF_DEPARTMENT.equals(referenceInfoDo.getReferenceRole())) {
            referenceInfoDo.setStatus(ReferenceStatus.UNAUDITED);
        } else {
            referenceInfoDo.setStatus(ReferenceStatus.ADOPT);
        }
        UserInfoDO userInfoDo = ReferenceUtil.transformUserInfo(referenceRegisterReq);
        this.referenceService.registerReference(referenceInfoDo, userInfoDo, referenceRegisterReq.getName());
        return this.userService.userInfoLogin(userInfoDo);
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PostMapping
    public boolean addReference(@RequestBody ReferenceAddReq referenceAddReq) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        ReferenceInfoDO referenceInfoDo = ReferenceUtil.transformReferenceInfoDo(referenceAddReq);
        referenceInfoDo.setStatus(ReferenceStatus.ADOPT);
        UserInfoDO userInfoDo = ReferenceUtil.transformUserInfo(referenceAddReq);
        this.referenceService.registerReference(referenceInfoDo, userInfoDo, loginInfo.getRealName());
        return true;
    }

    @RequiresUser
    @PutMapping("/{referenceId:\\d+}")
    public boolean updateReference(@PathVariable Long referenceId, @RequestBody ReferenceAddReq referenceAddReq) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        ReferenceInfoDO referenceInfoDo = ReferenceUtil.transformReferenceInfoDo(referenceAddReq);
        referenceInfoDo.setId(referenceId);
        UserInfoDO userInfoDo = ReferenceUtil.transformUserInfo(referenceAddReq);
        this.referenceService.updateReference(referenceInfoDo, userInfoDo, loginInfo.getRealName());
        return true;
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PutMapping("/{referenceId:\\d+}/status/{status:\\d+}")
    public boolean updateReferenceStatus(@PathVariable Long referenceId, @PathVariable Integer status) {
        ReferenceStatus referenceStatus = ReferenceStatus.parse(status);
        if (referenceStatus == null) {
            throw new RecruitmentException(RecruitmentErrorCode.REFERENCE_STATUS_CODE_EXIST);
        }
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        this.referenceService.updateReferenceStatus(referenceId, referenceStatus, loginInfo.getRealName());
        return true;
    }

    @GetMapping
    public PageResult<ReferenceDetailInfoRes> getReference(@MyParameter ReferenceQueryReq queryReq) {
        ReferenceInfoQuery query = ReferenceUtil.transformReferenceQuery(queryReq);
        return this.referenceService.getReference(query, queryReq.getPaginator());
    }

    @GetMapping("/{referenceId:\\d+}")
    public ReferenceDetailInfoRes getReference(@PathVariable Long referenceId) {
        return this.referenceService.getReference(referenceId);
    }
}
