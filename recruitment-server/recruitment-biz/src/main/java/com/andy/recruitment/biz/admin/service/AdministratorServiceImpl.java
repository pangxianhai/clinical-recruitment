package com.andy.recruitment.biz.admin.service;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.admin.constant.AdministratorStatus;
import com.andy.recruitment.dao.admin.dao.AdministratorDAO;
import com.andy.recruitment.dao.admin.entity.AdministratorInfoDO;
import com.soyoung.base.auth.LoginInfo;
import com.soyoung.base.auth.RoleType;
import com.soyoung.base.constant.Constant;
import com.soyoung.base.util.JsonUtil;
import com.soyoung.base.util.asserts.AssertUtil;
import com.soyoung.base.util.encrypt.HmacHashUtil;
import com.soyoung.base.util.encrypt.Rc4Util;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员服务实现
 *
 * @author 庞先海 2020-01-28
 */
@Service
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorDAO administratorDAO;

    @Autowired
    public AdministratorServiceImpl(AdministratorDAO administratorDAO) {
        this.administratorDAO = administratorDAO;
    }

    @Override
    public LoginInfo passwordLogin(String userName, String password) {
        AdministratorInfoDO administratorInfoDo = this.administratorDAO.getAdministratorInfo(userName);
        AssertUtil.assertNull(administratorInfoDo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ADMINISTRATOR_USER_NOT_EXIST);
        });
        String inputPassword = HmacHashUtil.hmacSha256Hash(password, administratorInfoDo.getUserName());
        AssertUtil.assertTrue(administratorInfoDo.getPassword().equals(inputPassword), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ADMINISTRATOR_PASSWORD_ERROR);
        });
        AssertUtil.assertFalse(AdministratorStatus.FREEZE.equals(administratorInfoDo.getStatus()), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ADMINISTRATOR_FREEZE);
        });
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserName(administratorInfoDo.getUserName());
        loginInfo.setAttach(String.valueOf(administratorInfoDo.getType().getCode()));
        loginInfo.setRealName(administratorInfoDo.getName());
        loginInfo.setRoleType(RoleType.MANAGER);
        loginInfo.setUserId(administratorInfoDo.getId());
        String sourceStr = JsonUtil.toJson(loginInfo);
        byte[] encodeByte = Rc4Util.rc4(sourceStr.getBytes(Constant.DEFAULT_CHARSET), loginInfo.getUserName());
        loginInfo.setToken(Base64.getEncoder().encodeToString(encodeByte));
        return loginInfo;
    }

    @Override
    public LoginInfo tokenLogin(String userName, String token) {
        try {
            byte[] encodeByte = Base64.getDecoder().decode(token);
            String sourceStr = new String(Rc4Util.rc4(encodeByte, userName), Constant.DEFAULT_CHARSET);
            LoginInfo loginInfo = JsonUtil.fromJson(sourceStr, LoginInfo.class);
            if (loginInfo == null) {
                throw new RecruitmentException(RecruitmentErrorCode.ADMINISTRATOR_ACCOUNT_ERROR);
            }
            return loginInfo;
        } catch (Exception e) {
            throw new RecruitmentException(RecruitmentErrorCode.ADMINISTRATOR_ACCOUNT_ERROR, e);
        }
    }
}
