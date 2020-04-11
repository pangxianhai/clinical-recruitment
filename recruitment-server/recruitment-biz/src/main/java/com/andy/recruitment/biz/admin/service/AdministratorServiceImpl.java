package com.andy.recruitment.biz.admin.service;

import com.andy.recruitment.api.admin.response.AdminInfoDetailRes;
import com.andy.recruitment.api.user.response.UserInfoRes;
import com.andy.recruitment.biz.user.service.UserService;
import com.andy.recruitment.common.admin.constant.AdministratorStatus;
import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.admin.dao.AdministratorDAO;
import com.andy.recruitment.dao.admin.entity.AdministratorInfoDO;
import com.andy.recruitment.dao.admin.entity.AdministratorQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.spring.auth.LoginInfo;
import com.andy.spring.auth.RoleType;
import com.andy.spring.constant.Constant;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import com.andy.spring.util.JsonUtil;
import com.andy.spring.util.asserts.AssertUtil;
import com.andy.spring.util.encrypt.HmacHashUtil;
import com.andy.spring.util.encrypt.Rc4Util;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 管理员服务实现
 *
 * @author 庞先海 2020-01-28
 */
@Service
public class AdministratorServiceImpl implements AdministratorService {

    private final UserService userService;

    private final AdministratorDAO administratorDAO;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public AdministratorServiceImpl(UserService userService, AdministratorDAO administratorDAO,
        TransactionTemplate transactionTemplate) {
        this.userService = userService;
        this.administratorDAO = administratorDAO;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public LoginInfo passwordLogin(String phone, String password) {
        UserInfoDO userInfoDo = this.userService.getUserInfoDoByPhone(phone);
        AssertUtil.assertNull(userInfoDo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ADMINISTRATOR_USER_NOT_EXIST);
        });
        AdministratorInfoDO administratorInfoDo = this.administratorDAO.getAdministratorByUserId(userInfoDo.getId());
        AssertUtil.assertNull(administratorInfoDo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ADMINISTRATOR_USER_NOT_EXIST);
        });
        String inputPassword = HmacHashUtil.hmacSha256Hash(password, userInfoDo.getPhone());
        AssertUtil.assertTrue(userInfoDo.getPassword().equals(inputPassword), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ADMINISTRATOR_PASSWORD_ERROR);
        });
        AssertUtil.assertFalse(AdministratorStatus.FREEZE.equals(administratorInfoDo.getStatus()), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ADMINISTRATOR_FREEZE);
        });
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserName(userInfoDo.getPhone());
        loginInfo.setAttach(String.valueOf(administratorInfoDo.getType().getCode()));
        loginInfo.setRealName(userInfoDo.getRealName());
        loginInfo.setRoleType(RoleType.MANAGER);
        loginInfo.setUserId(administratorInfoDo.getId());
        String sourceStr = JsonUtil.toJson(loginInfo);
        byte[] encodeByte = Rc4Util.rc4(sourceStr.getBytes(Constant.DEFAULT_CHARSET), loginInfo.getUserName());
        loginInfo.setToken(Base64.getUrlEncoder().encodeToString(encodeByte));
        return loginInfo;
    }

    @Override
    public PageResult<AdminInfoDetailRes> listAdministratorInfo(AdministratorQuery query, Paginator paginator) {
        PageResult<AdministratorInfoDO> pageResult = this.administratorDAO.listAdministratorInfo(query, paginator);
        List<AdminInfoDetailRes> adminInfoResList = this.transformAdminInfoRes(pageResult.getData());
        return new PageResult<>(adminInfoResList, pageResult.getPaginator());
    }

    @Override
    public void updateAdminStatus(Long adminId, AdministratorStatus status, String operator) {
        AdministratorInfoDO administratorInfoDo = new AdministratorInfoDO();
        administratorInfoDo.setId(adminId);
        administratorInfoDo.setStatus(status);
        this.administratorDAO.updateAdministrator(administratorInfoDo, operator);
    }

    @Override
    public void updateAdministrator(AdministratorInfoDO administratorInfoDo, UserInfoDO userInfoDo, String operator) {
        AdministratorInfoDO sourceAdminInfoDo = this.administratorDAO.getAdministratorById(administratorInfoDo.getId());
        AssertUtil.assertNull(sourceAdminInfoDo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.ADMINISTRATOR_USER_NOT_EXIST);
        });
        this.transactionTemplate.execute(status -> {
            this.administratorDAO.updateAdministrator(administratorInfoDo, operator);
            userInfoDo.setId(sourceAdminInfoDo.getUserId());
            this.userService.updateUserInfo(userInfoDo, operator);
            return null;
        });
    }

    private List<AdminInfoDetailRes> transformAdminInfoRes(List<AdministratorInfoDO> administratorInfoDoList) {
        if (CollectionUtils.isEmpty(administratorInfoDoList)) {
            return null;
        }
        List<Long> userIdList = administratorInfoDoList.stream().map(AdministratorInfoDO::getUserId).filter(
            Objects::nonNull).collect(Collectors.toList());
        Map<Long, UserInfoRes> userInfoResMap = this.userService.getUserInfoRes(userIdList);
        return administratorInfoDoList.stream().map(infoDo -> {
            if (infoDo == null) {
                return null;
            }
            AdminInfoDetailRes adminInfoDetailRes = new AdminInfoDetailRes();
            BeanUtils.copyProperties(infoDo, adminInfoDetailRes);
            adminInfoDetailRes.setUserInfoRes(userInfoResMap.get(infoDo.getUserId()));
            adminInfoDetailRes.setAdminId(infoDo.getId());
            return adminInfoDetailRes;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
