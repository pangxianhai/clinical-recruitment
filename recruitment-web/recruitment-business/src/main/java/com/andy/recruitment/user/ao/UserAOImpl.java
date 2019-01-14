package com.andy.recruitment.user.ao;

import com.andy.recruitment.user.constant.Gender;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.user.service.UserInfoService;
import com.andy.recruitment.weixin.model.OauthToken;
import com.andy.recruitment.weixin.model.WxUserInfo;
import com.andy.recruitment.weixin.service.WeiXinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户AO
 *
 * @author 庞先海 2018-12-28
 */
@Component
public class UserAOImpl implements UserAO {

    private final UserInfoService userInfoService;

    private final WeiXinService weiXinService;

    @Autowired
    public UserAOImpl(UserInfoService userInfoService, WeiXinService weiXinService) {
        this.userInfoService = userInfoService;
        this.weiXinService = weiXinService;
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

    @Override
    public UserInfo loginByWeixin(String code) {
        OauthToken oauthToken = this.weiXinService.getOauthAccessToken(code);
        UserInfo userInfo = this.userInfoService.getUserInfoByOpenId(oauthToken.getOpenId());
        if (null != userInfo) {
            return userInfo;
        }
        WxUserInfo wxUserInfo = this.weiXinService.getWxUserInfo(oauthToken.getAccessToken(), oauthToken.getOpenId());
        UserInfo addUserInfo = new UserInfo();
        addUserInfo.setOpenId(wxUserInfo.getOpenId());
        addUserInfo.setUnionId(wxUserInfo.getUnionid());
        addUserInfo.setGender(Gender.parse(wxUserInfo.getSex()));
        addUserInfo.setRealName(wxUserInfo.getNickname());
        Long userId = this.userInfoService.addUserInfo(addUserInfo, addUserInfo.getRealName());
        addUserInfo.setUserId(userId);
        return addUserInfo;
    }
}
