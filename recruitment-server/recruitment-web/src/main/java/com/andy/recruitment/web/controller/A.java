package com.andy.recruitment.web.controller;

import com.andy.recruitment.biz.admin.service.AdministratorService;
import com.andy.recruitment.web.controller.admin.response.AdminLoginRes;
import com.soyoung.base.auth.LoginInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述
 *
 * @author 庞先海 2020-01-27
 */
@RestController
public class A {

    private final AdministratorService administratorService;

    @Autowired
    public A(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @RequiresUser
    @GetMapping("/a")
    public String s() {
        return "ss";
    }

    @RequiresRoles("admin")
    @RequiresPermissions("add")
    @GetMapping("/b")
    public String b() {
        return "ssbb";
    }

    @GetMapping("/login")
    public AdminLoginRes login() {
        LoginInfo loginInfo = administratorService.passwordLogin("pxh", "123456");
        UsernamePasswordToken token = new UsernamePasswordToken(loginInfo.getUserName(), loginInfo.getToken());
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        AdminLoginRes adminLoginRes = new AdminLoginRes();
        adminLoginRes.setToken(loginInfo.getToken());
        adminLoginRes.setUserName(loginInfo.getUserName());
        return adminLoginRes;
    }

}
