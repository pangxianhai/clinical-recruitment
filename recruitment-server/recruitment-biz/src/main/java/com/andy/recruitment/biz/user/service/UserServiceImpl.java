package com.andy.recruitment.biz.user.service;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.user.constant.Gender;
import com.andy.recruitment.dao.user.dao.UserDAO;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.manager.weixin.entity.OauthToken;
import com.andy.recruitment.manager.weixin.entity.WxUserInfo;
import com.andy.recruitment.manager.weixin.service.WeiXinManagerService;
import com.andy.spring.auth.LoginInfo;
import com.andy.spring.auth.RoleType;
import com.andy.spring.constant.Constant;
import com.andy.spring.util.JsonUtil;
import com.andy.spring.util.asserts.AssertUtil;
import com.andy.spring.util.encrypt.Rc4Util;
import java.util.Base64;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务接口实现
 *
 * @author 庞先海 2020-02-02
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final WeiXinManagerService weiXinManagerService;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, WeiXinManagerService weiXinManagerService) {
        this.userDAO = userDAO;
        this.weiXinManagerService = weiXinManagerService;
    }

    @Override
    public UserInfoDO getUserInfoByPhone(String phone) {
        return this.userDAO.getUserInfoByPhone(phone);
    }

    @Override
    public List<UserInfoDO> getUserInfo(List<Long> userIdList) {
        return this.userDAO.getUserInfo(userIdList);
    }

    @Override
    public UserInfoDO getUserInfoByUserId(Long userId) {
        return this.userDAO.getUserInfoByUserId(userId);
    }

    @Override
    public UserInfoDO wxLogin(String code) {
        OauthToken oauthToken = this.weiXinManagerService.getOauthAccessToken(code);
        AssertUtil.assertNull(oauthToken, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.USER_REGISTER_FAILED);
        });
        WxUserInfo wxUserInfo = this.weiXinManagerService.getWxUserInfo(oauthToken.getAccessToken(),
            oauthToken.getOpenId());
        AssertUtil.assertNull(wxUserInfo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.USER_REGISTER_FAILED);
        });
        UserInfoDO userInfoDo = this.userDAO.getUserInfoByOpenId(oauthToken.getOpenId());
        if (userInfoDo != null) {
            if (! wxUserInfo.getNickname().equals(userInfoDo.getNickname())) {
                UserInfoDO updateUserInfo = new UserInfoDO();
                updateUserInfo.setId(userInfoDo.getId());
                updateUserInfo.setNickname(wxUserInfo.getNickname());
                this.userDAO.updateUserInfo(updateUserInfo, userInfoDo.getRealName());
                userInfoDo.setNickname(wxUserInfo.getNickname());
            }
            return userInfoDo;
        } else {
            UserInfoDO addUserInfo = new UserInfoDO();
            addUserInfo.setOpenId(wxUserInfo.getOpenId());
            addUserInfo.setUnionId(wxUserInfo.getUnionid());
            addUserInfo.setGender(Gender.parse(wxUserInfo.getSex()));
            addUserInfo.setRealName(wxUserInfo.getNickname());
            addUserInfo.setNickname(wxUserInfo.getNickname());
            //此时不保存用户信息 用户信息入库必须有手机号此时还不能获取手机号 暂不入库
            return addUserInfo;
        }
    }

    @Override
    public LoginInfo userInfoLogin(UserInfoDO userInfoDo) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserName(userInfoDo.getPhone());
        loginInfo.setRealName(userInfoDo.getRealName());
        loginInfo.setRoleType(RoleType.CUSTOMER);
        loginInfo.setUserId(userInfoDo.getId());
        String sourceStr = JsonUtil.toJson(loginInfo);
        byte[] encodeByte = Rc4Util.rc4(sourceStr.getBytes(Constant.DEFAULT_CHARSET), loginInfo.getUserName());
        loginInfo.setToken(Base64.getUrlEncoder().encodeToString(encodeByte));
        UsernamePasswordToken token = new UsernamePasswordToken(loginInfo.getUserName(), loginInfo.getToken());
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        return loginInfo;
    }
}
