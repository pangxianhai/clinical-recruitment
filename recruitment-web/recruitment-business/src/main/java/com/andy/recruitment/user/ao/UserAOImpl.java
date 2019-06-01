package com.andy.recruitment.user.ao;

import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
import com.andy.recruitment.exception.RecruitmentErrorCode;
import com.andy.recruitment.exception.RecruitmentException;
import com.andy.recruitment.sms.service.SmsService;
import com.andy.recruitment.user.constant.Gender;
import com.andy.recruitment.user.constant.UserStatus;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.user.model.UserQueryParam;
import com.andy.recruitment.user.service.UserInfoService;
import com.andy.recruitment.weixin.model.OauthToken;
import com.andy.recruitment.weixin.model.WxUserInfo;
import com.andy.recruitment.weixin.service.WeiXinService;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;
import com.xgimi.commons.util.RandomUtil;
import com.xgimi.commons.util.StringUtil;
import com.xgimi.commons.util.asserts.AssertUtil;
import com.xgimi.commons.util.encrypt.HmacHashUtil;
import com.xgimi.context.ServletContext;
import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
    public Long registerUser(UserInfo userInfo, String operator) {
        return this.userInfoService.registerUser(userInfo, operator);
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
        WxUserInfo wxUserInfo = this.weiXinService.getWxUserInfo(oauthToken.getAccessToken(), oauthToken.getOpenId());
        UserInfo userInfo = this.userInfoService.getUserInfoByOpenId(oauthToken.getOpenId());
        if (null != userInfo) {
            if (! wxUserInfo.getNickname().equals(userInfo.getNickname())) {
                UserInfo updateUserInfo = new UserInfo();
                updateUserInfo.setUserId(userInfo.getUserId());
                updateUserInfo.setNickname(wxUserInfo.getNickname());
                this.userInfoService.updateUserInfo(updateUserInfo, userInfo.getRealName());
                userInfo.setNickname(wxUserInfo.getNickname());
            }
            return userInfo;
        } else {
            UserInfo addUserInfo = new UserInfo();
            addUserInfo.setOpenId(wxUserInfo.getOpenId());
            addUserInfo.setUnionId(wxUserInfo.getUnionid());
            addUserInfo.setGender(Gender.parse(wxUserInfo.getSex()));
            addUserInfo.setRealName(wxUserInfo.getNickname());
            addUserInfo.setNickname(wxUserInfo.getNickname());
            //            Long userId = this.userInfoService.addUserInfo(addUserInfo, addUserInfo.getRealName());
            //            addUserInfo.setUserId(userId);
            return addUserInfo;
        }
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
        UserInfo userInfoByPhone = this.userInfoService.getUserInfoByPhone(phone);
        if (null != userInfoByPhone && ! userInfoByPhone.getUserId().equals(userId)) {
            throw new BusinessException(BusinessErrorCode.USER_PHONE_HAS_REGISTER);
        }

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

    @Override
    public void updateUserStatus(Long userId, UserStatus status, String operator) {
        if (UserStatus.FREEZE.equals(status)) {
            this.checkFreezeManager(userId);
        }
        this.userInfoService.updateUserStatus(userId, status, operator);
    }

    @Override
    public void updatePassword(Long userId, String password, String newPassword, String operator) {
        UserInfo userInfo = this.userInfoService.getUserInfoByUserId(userId);
        AssertUtil.assertNull(userInfo, () -> {
            throw new BusinessException(BusinessErrorCode.USER_NOT_EXIST);
        });
        String pas = HmacHashUtil.hmacSHAHash(password, userInfo.getPhone());
        AssertUtil.assertBoolean(pas.equals(userInfo.getPassword()), () -> {
            throw new BusinessException(BusinessErrorCode.USER_PASSWORD_ERROR);
        });
        UserInfo updateUserInfo = new UserInfo();
        updateUserInfo.setUserId(userId);
        updateUserInfo.setPassword(HmacHashUtil.hmacSHAHash(newPassword, userInfo.getPhone()));
        this.userInfoService.updateUserInfo(updateUserInfo, operator);
    }

    @Override
    public UserInfo loginByPhone(String phone, String password) {
        UserInfo userInfo = this.userInfoService.getUserInfoByPhone(phone);
        AssertUtil.assertNull(userInfo, () -> {
            throw new BusinessException(BusinessErrorCode.USER_NOT_EXIST);
        });
        String pas = HmacHashUtil.hmacSHAHash(password, phone);
        AssertUtil.assertBoolean(pas.equals(userInfo.getPassword()), () -> {
            throw new BusinessException(BusinessErrorCode.USER_PASSWORD_ERROR);
        });
        AssertUtil.assertBoolean(! UserStatus.FREEZE.equals(userInfo.getStatus()), () -> {
            throw new BusinessException(BusinessErrorCode.USER_FREEZE);
        });
        return userInfo;
    }

    @Override
    public PageResult<UserInfo> getUserInfo(UserQueryParam queryParam, Paginator paginator) {
        return this.userInfoService.getUserInfo(queryParam, paginator);
    }

    @Override
    public void saveUserInfoCookie(UserInfo userInfo, HttpServletResponse response) {
        if (null == userInfo || null == userInfo.getUserId() || null == response) {
            return;
        }
        Cookie cookie = new Cookie("userId", String.valueOf(userInfo.getUserId()));
        cookie.setMaxAge(- 1);
        cookie.setPath("/");
        ServletContext.getResponse().addCookie(cookie);
    }

    @Override
    public UserInfo getUserInfoByPhone(String phone) {
        return this.userInfoService.getUserInfoByPhone(phone);
    }

    @Override
    public void delete(Long userId) {
        this.userInfoService.delete(userId);
    }

    private void checkFreezeManager(Long userId) {
        UserInfo userInfo = this.userInfoService.getUserInfoByUserId(userId);
        AssertUtil.assertNull(userInfo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.USER_ID_EMPTY);
        });
        if (! UserType.ADMIN.equals(userInfo.getUserType())) {
            return;
        }
        UserQueryParam queryParam = new UserQueryParam();
        queryParam.setUserType(UserType.ADMIN);
        queryParam.setStatus(UserStatus.NORMAL);
        Paginator paginator = new Paginator(1, 1);
        PageResult<UserInfo> pageResult = this.userInfoService.getUserInfo(queryParam, paginator);
        if (pageResult.getPaginator().getTotalRecord() != 1) {
            return;
        }
        UserInfo normalUser = pageResult.getData().get(0);
        if (normalUser.getUserId().equals(userId)) {
            throw new RecruitmentException(RecruitmentErrorCode.USER_MANAGER_MUST_ONE);
        }
    }
}
