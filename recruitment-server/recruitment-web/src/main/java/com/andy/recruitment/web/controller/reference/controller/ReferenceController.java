package com.andy.recruitment.web.controller.reference.controller;

import com.andy.recruitment.biz.reference.service.ReferenceService;
import com.andy.recruitment.biz.user.service.UserService;
import com.andy.recruitment.dao.reference.constant.ReferenceStatus;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.reference.request.ReferenceAddReq;
import com.andy.recruitment.web.controller.reference.request.ReferenceQueryReq;
import com.andy.recruitment.web.controller.reference.request.ReferenceRegisterReq;
import com.andy.recruitment.web.controller.reference.response.ReferenceDetailInfoRes;
import com.andy.recruitment.web.controller.reference.util.ReferenceUtil;
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

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PostMapping
    public boolean addReference(@RequestBody ReferenceAddReq referenceAddReq) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        ReferenceInfoDO referenceInfoDo = ReferenceUtil.transformReferenceInfoDo(referenceAddReq);
        referenceInfoDo.setStatus(ReferenceStatus.NORMAL);
        UserInfoDO userInfoDo = ReferenceUtil.transformUserInfo(referenceAddReq);
        this.referenceService.registerReference(referenceInfoDo, userInfoDo, loginInfo.getRealName());
        return true;
    }

    @PostMapping("register")
    public LoginInfo registerReference(@RequestBody ReferenceRegisterReq referenceRegisterReq) {
        ReferenceInfoDO referenceInfoDo = ReferenceUtil.transformReferenceInfoDo(referenceRegisterReq);
        referenceInfoDo.setStatus(ReferenceStatus.NORMAL);
        UserInfoDO userInfoDo = ReferenceUtil.transformUserInfo(referenceRegisterReq);
        this.referenceService.registerReference(referenceInfoDo, userInfoDo, referenceRegisterReq.getName());
        return this.userService.userInfoLogin(userInfoDo);
    }

    @GetMapping
    public PageResult<ReferenceDetailInfoRes> getReference(@MyParameter ReferenceQueryReq queryReq) {
        ReferenceInfoQuery query = ReferenceUtil.transformReferenceQuery(queryReq);
        PageResult<ReferenceInfoDO> pageResult = this.referenceService.getReference(query, queryReq.getPaginator());
        List<ReferenceDetailInfoRes> referenceDetailInfoResList = ReferenceUtil.transformReferenceDetailRes(
            pageResult.getData());
        return new PageResult<>(referenceDetailInfoResList, pageResult.getPaginator());
    }

    @GetMapping("/{referenceId:\\d+}")
    public ReferenceDetailInfoRes getReference(@PathVariable Long referenceId) {
        ReferenceInfoDO referenceInfoDo = this.referenceService.getReference(referenceId);
        return ReferenceUtil.transformReferenceDetailRes(referenceInfoDo);
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

}
