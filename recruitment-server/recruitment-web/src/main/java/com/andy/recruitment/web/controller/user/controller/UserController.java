package com.andy.recruitment.web.controller.user.controller;

import com.andy.recruitment.biz.user.service.UserService;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.user.response.UserInfoRes;
import com.andy.recruitment.web.controller.user.util.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户controller接口
 *
 * @author 庞先海 2020-02-02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/phone/{phone:\\d+}")
    public UserInfoRes getUserByPhone(@PathVariable String phone) {
        UserInfoDO userInfoDo = this.userService.getUserInfoByPhone(phone);
        return UserInfoUtil.transformUserInfoRes(userInfoDo);
    }
}
