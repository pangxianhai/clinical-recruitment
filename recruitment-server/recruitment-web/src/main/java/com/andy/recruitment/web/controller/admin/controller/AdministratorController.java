package com.andy.recruitment.web.controller.admin.controller;

import com.andy.recruitment.biz.admin.service.AdministratorService;
import com.andy.recruitment.web.controller.admin.request.AdminLoginReq;
import com.andy.recruitment.web.controller.admin.response.AdminLoginRes;
import com.soyoung.base.auth.LoginInfo;
import com.soyoung.base.auth.RoleType;
import com.soyoung.base.context.ServletContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
}
