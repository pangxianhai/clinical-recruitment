package com.andy.recruitment.user.ao;

import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户AO
 *
 * @author 庞先海 2018-12-28
 */
public class UserAOImpl implements UserAO {

    private final UserInfoService userInfoService;

    @Autowired
    public UserAOImpl(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public void addUserInfo(UserInfo userInfo, String operator) {
        this.userInfoService.addUserInfo(userInfo, operator);
    }

    @Override
    public void updateUserInfo(UserInfo userInfo, String operator) {
        this.userInfoService.updateUserInfo(userInfo, operator);
    }

    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        return this.userInfoService.getUserInfoByUserId(userId);
    }

    @Override
    public UserInfo getUserInfoByOpenId(String openId) {
        return this.userInfoService.getUserInfoByOpenId(openId);
    }
}
