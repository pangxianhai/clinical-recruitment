package com.andy.recruitment.web.controller;

import com.soyoung.base.auth.LoginInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述
 *
 * @author 庞先海 2020-01-27
 */
@RestController
public class A {

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
    public LoginInfo login() {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("pxh", "1234");
        try {
            currentUser.login(token);
            Session session = SecurityUtils.getSubject().getSession();
            LoginInfo loginInfo = (LoginInfo)session.getAttribute("aaa");

            return loginInfo;
        } catch (AuthenticationException e) {
            throw new RuntimeException("登录失败");
        }
    }

}
