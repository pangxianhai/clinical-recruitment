package com.andy.recruitment.user.ao;

import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
import com.andy.recruitment.sms.service.SmsService;
import com.andy.recruitment.user.constant.Gender;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.user.service.UserInfoService;
import com.andy.recruitment.weixin.model.OauthToken;
import com.andy.recruitment.weixin.model.WxUserInfo;
import com.andy.recruitment.weixin.service.WeiXinService;
import com.xgimi.commons.util.RandomUtil;
import com.xgimi.commons.util.StringUtil;
import com.xgimi.commons.util.asserts.AssertUtil;
import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 用户AO
 *
 * @author 庞先海 2018-12-28
 */
@Component
public class UserAOImpl implements UserAO {

    private static Map<String, String> verCodeMap = new ConcurrentHashMap<>(16);

    @Value("${login.ver.code}")
    private String loginVerCodeModule;

    @Value("${manager.login.phone}")
    private String managerPhone;

    private final UserInfoService userInfoService;

    private final WeiXinService weiXinService;

    private final SmsService smsService;

    @Autowired
    public UserAOImpl(UserInfoService userInfoService, WeiXinService weiXinService, SmsService smsService) {
        this.userInfoService = userInfoService;
        this.weiXinService = weiXinService;
        this.smsService = smsService;
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

    @Override
    public void sendLoginVerCode(String phone) {
        if (StringUtil.isEmpty(phone)) {
            return;
        }
        String verCode = RandomUtil.getRandCode(6, true);
        verCodeMap.put(phone, verCode);
        String message = MessageFormat.format(loginVerCodeModule, verCode);
        this.smsService.sendMessage(phone, message);
    }

    @Override
    public void bandPhone(Long userId, String phone, String verCode) {
        UserInfo userInfo = this.userInfoService.getUserInfoByUserId(userId);
        AssertUtil.assertNull(userInfo, () -> {
            throw new BusinessException(BusinessErrorCode.USER_NOT_EMPTY);
        });
        UserInfo updateUserInfo = new UserInfo();
        if (managerPhone.equals(phone)) {
            updateUserInfo.setUserType(UserType.ADMIN);
        }
        updateUserInfo.setUserId(userId);
        updateUserInfo.setPhone(phone);

        this.userInfoService.updateUserInfo(updateUserInfo, userInfo.getRealName());
    }

    @Override
    public void updateUserInfo(UserInfo userInfo, String operator) {
        this.userInfoService.updateUserInfo(userInfo, operator);
    }

    private String getLoginVerCode(String phone) {
        if (StringUtil.isEmpty(phone)) {
            return null;
        }
        return verCodeMap.get(phone);
    }
}
