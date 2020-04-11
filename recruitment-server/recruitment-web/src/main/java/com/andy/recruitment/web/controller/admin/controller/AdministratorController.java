package com.andy.recruitment.web.controller.admin.controller;

import com.andy.recruitment.api.admin.request.AdminLoginReq;
import com.andy.recruitment.api.admin.request.AdminQueryReq;
import com.andy.recruitment.api.admin.response.AdminInfoDetailRes;
import com.andy.recruitment.api.admin.response.AdminLoginRes;
import com.andy.recruitment.biz.admin.service.AdministratorService;
import com.andy.recruitment.common.admin.constant.AdminType;
import com.andy.recruitment.common.admin.constant.AdministratorStatus;
import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.admin.entity.AdministratorQuery;
import com.andy.spring.auth.LoginInfo;
import com.andy.spring.auth.RoleType;
import com.andy.spring.context.ServletContext;
import com.andy.spring.converter.MyParameter;
import com.andy.spring.page.PageResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员controller
 *
 * @author 庞先海 2020-01-28
 */
@RestController
@RequestMapping("/administrator")
public class AdministratorController {

    private final AdministratorService administratorService;

    @Autowired
    public AdministratorController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    /**
     * 管理员登录
     */
    @PostMapping("/login")
    public AdminLoginRes administratorLogin(@RequestBody AdminLoginReq loginReq) {
        LoginInfo loginInfo = administratorService.passwordLogin(loginReq.getUserName(), loginReq.getPassword());
        UsernamePasswordToken token = new UsernamePasswordToken(loginInfo.getUserName(), loginInfo.getToken());
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        AdminLoginRes adminLoginRes = new AdminLoginRes();
        adminLoginRes.setToken(loginInfo.getToken());
        adminLoginRes.setUserName(loginInfo.getUserName());
        return adminLoginRes;
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @GetMapping("/loginInfo")
    public LoginInfo getAdministratorLoginInfo() {
        return ServletContext.getLoginInfo();
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @GetMapping
    public PageResult<AdminInfoDetailRes> listAdminInfo(@MyParameter AdminQueryReq queryReq) {
        AdministratorQuery query = new AdministratorQuery();
        BeanUtils.copyProperties(queryReq, query);
        query.setStatus(AdministratorStatus.parse(queryReq.getStatus()));
        query.setType(AdminType.parse(queryReq.getType()));
        return this.administratorService.listAdministratorInfo(query, queryReq.getPaginator());
    }


    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PutMapping("/{adminId:\\d+}/status/{status:\\d+}")
    public boolean updateAdminStatus(@PathVariable Long adminId, @PathVariable Integer status) {
        AdministratorStatus adminStatus = AdministratorStatus.parse(status);
        if (adminStatus == null) {
            throw new RecruitmentException(RecruitmentErrorCode.ADMINISTRATOR_STATUS_CODE_EXIST);
        }
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        this.administratorService.updateAdminStatus(adminId, adminStatus, loginInfo.getRealName());
        return true;
    }
}
