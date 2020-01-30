package com.andy.recruitment.biz.admin.service;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.admin.constant.AdminType;
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
        loginInfo.setToken(Base64.getUrlEncoder().encodeToString(encodeByte));
        return loginInfo;
    }

    @Override
    public LoginInfo tokenLogin(String userName, String token) {
        try {
            byte[] encodeByte = Base64.getUrlDecoder().decode(token);
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

    public static void main(String... args) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserName("pxh");
        loginInfo.setAttach(String.valueOf(AdminType.BUSINESS_ADMIN.getCode()));
        loginInfo.setRealName("张三");
        loginInfo.setRoleType(RoleType.MANAGER);
        loginInfo.setUserId(1234566L);
        String sourceStr = JsonUtil.toJson(loginInfo);
        byte[] encodeByte = Rc4Util.rc4(sourceStr.getBytes(Constant.DEFAULT_CHARSET), loginInfo.getUserName());
        loginInfo.setToken(Base64.getUrlEncoder().encodeToString(encodeByte));
        System.out.println(loginInfo.getToken());

        String token = "cO0v-tbzdoXCSyNIVnnubZmlaaD-k5sNTS1txnqjEIGcI-wGi2EptU-3vN5mTGqr7ho91b8Qn1ydwigTB6mf0lHrpiZBYDRnevTla8xR-ES3WZRf96nDZFNFo2Wd9Wte1qSTBvulKMJdbA==";
        try {
            byte[] encodeByte1 = Base64.getUrlDecoder().decode(token);
            String sourceStr1 = new String(Rc4Util.rc4(encodeByte1, loginInfo.getUserName()), Constant.DEFAULT_CHARSET);
            LoginInfo loginInfo1 = JsonUtil.fromJson(sourceStr1, LoginInfo.class);
            if (encodeByte1 == null) {
                System.out.println("解密失败");
            }
            System.out.println("解密后结果:" + JsonUtil.toJson(loginInfo1));
        } catch (Exception e) {
            throw new RecruitmentException(RecruitmentErrorCode.ADMINISTRATOR_ACCOUNT_ERROR, e);
        }
    }
}
