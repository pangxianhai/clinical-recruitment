package com.andy.recruitment.user.service;

import com.andy.recruitment.exception.RecruitmentErrorCode;
import com.andy.recruitment.exception.RecruitmentException;
import com.andy.recruitment.user.constant.UserStatus;
import com.andy.recruitment.user.mapper.UserInfoMapper;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.user.model.UserInfoDO;
import com.andy.recruitment.user.model.UserQueryParam;
import com.andy.recruitment.user.util.UserUtil;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.commons.util.DateUtil;
import com.xgimi.commons.util.StringUtil;
import com.xgimi.commons.util.asserts.AssertUtil;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息服务
 *
 * @author 庞先海 2018-12-28
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoMapper userInfoMapper;

    @Autowired
    public UserInfoServiceImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public Long addUserInfo(UserInfo userInfo, String operator) {
        UserInfoDO userInfoDO = UserUtil.transformUserInfoDO(userInfo);
        userInfoDO.setStatus(UserStatus.NORMAL);
        userInfoDO.setCreatedBy(operator);
        userInfoDO.setCreatedTime(new Timestamp(DateUtil.currentMilliseconds()));
        int count = this.userInfoMapper.insert(userInfoDO);
        AssertUtil.assertBoolean(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.USER_ADD_FAILED);
        });
        return userInfoDO.getId();
    }

    @Override
    public void updateUserInfo(UserInfo userInfo, String operator) {
        AssertUtil.assertNull(userInfo.getUserId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.USER_ID_EMPTY);
        });
        UserInfoDO userInfoDO = UserUtil.transformUserInfoDO(userInfo);
        userInfoDO.setUpdatedBy(operator);
        userInfoDO.setUpdatedTime(new Timestamp(DateUtil.currentMilliseconds()));
        int count = this.userInfoMapper.update(userInfoDO);
        AssertUtil.assertBoolean(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.USER_UPDATE_FAILED);
        });
    }

    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        if (null == userId) {
            return null;
        }
        UserQueryParam queryParam = new UserQueryParam();
        queryParam.setUserId(userId);
        List<UserInfoDO> userInfoDOList = this.userInfoMapper.select(queryParam);
        return CollectionUtil.parseOne(userInfoDOList, UserUtil::transformUserInfo);
    }

    @Override
    public UserInfo getUserInfoByOpenId(String openId) {
        if (null == openId) {
            return null;
        }
        UserQueryParam queryParam = new UserQueryParam();
        queryParam.setOpenId(openId);
        List<UserInfoDO> userInfoDOList = this.userInfoMapper.select(queryParam);
        return CollectionUtil.parseOne(userInfoDOList, UserUtil::transformUserInfo);
    }

    @Override
    public UserInfo getUserInfoByPhone(String phone) {
        if (StringUtil.isEmpty(phone)) {
            return null;
        }
        UserQueryParam queryParam = new UserQueryParam();
        queryParam.setPhone(phone);
        List<UserInfoDO> userInfoDOList = this.userInfoMapper.select(queryParam);
        return CollectionUtil.parseOne(userInfoDOList, UserUtil::transformUserInfo);
    }
}
