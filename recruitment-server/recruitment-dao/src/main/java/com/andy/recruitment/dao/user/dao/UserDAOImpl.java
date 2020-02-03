package com.andy.recruitment.dao.user.dao;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.user.constant.UserStatus;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.dao.user.entity.UserQuery;
import com.andy.recruitment.dao.user.mapper.UserInfoMapper;
import com.soyoung.base.mybatis.paginator.Page;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.PageUtil;
import com.soyoung.base.page.Paginator;
import com.soyoung.base.util.CollectionUtil;
import com.soyoung.base.util.asserts.AssertUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户DAO接口实现
 *
 * @author 庞先海 2020-01-26
 */
@Service
public class UserDAOImpl implements UserDAO {

    private final UserInfoMapper userInfoMapper;

    @Autowired
    public UserDAOImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public Long registerUser(UserInfoDO userInfoDo, String operator) {
        UserInfoDO existUserInfo = this.getUserInfoByPhone(userInfoDo.getPhone());
        if (null == existUserInfo) {
            userInfoDo.setStatus(UserStatus.NORMAL);
            userInfoDo.setCreatedBy(operator);
            userInfoDo.setCreatedTime(LocalDateTime.now());
            int count = this.userInfoMapper.insert(userInfoDo);
            AssertUtil.assertTrue(count > 0, () -> {
                throw new RecruitmentException(RecruitmentErrorCode.USER_ADD_FAILED);
            });
            return userInfoDo.getId();
        } else {
            userInfoDo.setId(existUserInfo.getId());
            this.updateUserInfo(userInfoDo, operator);
            return existUserInfo.getId();
        }
    }

    @Override
    public void updateUserInfo(UserInfoDO userInfoDo, String operator) {
        AssertUtil.assertNull(userInfoDo.getId(), () -> {
            throw new RecruitmentException(RecruitmentErrorCode.USER_ID_EMPTY);
        });
        userInfoDo.setUpdatedBy(operator);
        userInfoDo.setUpdatedTime(LocalDateTime.now());
        int count = this.userInfoMapper.update(userInfoDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.USER_UPDATE_FAILED);
        });
    }

    @Override
    public void updateUserStatus(Long userId, UserStatus status, String operator) {
        AssertUtil.assertNull(userId, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.USER_ID_EMPTY);
        });
        UserInfoDO userInfoDO = new UserInfoDO();
        userInfoDO.setId(userId);
        userInfoDO.setStatus(status);
        userInfoDO.setUpdatedBy(operator);
        userInfoDO.setUpdatedTime(LocalDateTime.now());
        int count = this.userInfoMapper.update(userInfoDO);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.USER_UPDATE_FAILED);
        });
    }

    @Override
    public UserInfoDO getUserInfoByUserId(Long userId) {
        if (null == userId) {
            return null;
        }
        UserQuery queryParam = new UserQuery();
        queryParam.setUserId(userId);
        List<UserInfoDO> userInfoDoList = this.userInfoMapper.select(queryParam);
        return CollectionUtil.parseOne(userInfoDoList, t -> t);
    }

    @Override
    public UserInfoDO getUserInfoByOpenId(String openId) {
        if (StringUtils.isEmpty(openId)) {
            return null;
        }
        UserQuery queryParam = new UserQuery();
        queryParam.setOpenId(openId);
        List<UserInfoDO> userInfoDoList = this.userInfoMapper.select(queryParam);
        return CollectionUtil.parseOne(userInfoDoList, t -> t);
    }

    @Override
    public UserInfoDO getUserInfoByPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return null;
        }
        UserQuery queryParam = new UserQuery();
        queryParam.setPhone(phone);
        List<UserInfoDO> userInfoDoList = this.userInfoMapper.select(queryParam);
        return CollectionUtil.parseOne(userInfoDoList, t -> t);
    }

    @Override
    public PageResult<UserInfoDO> getUserInfo(UserQuery queryParam, Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<UserInfoDO> userInfoDoList = this.userInfoMapper.select(queryParam, page);
        return new PageResult<>(userInfoDoList, PageUtil.transformToPaginator(page));
    }

    @Override
    public List<UserInfoDO> getUserInfo(List<Long> userIdList) {
        if (CollectionUtils.isEmpty(userIdList)) {
            return null;
        }
        userIdList = userIdList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userIdList)) {
            return null;
        }
        UserQuery queryParam = new UserQuery();
        queryParam.setUserIdLIst(userIdList);
        return this.userInfoMapper.select(queryParam);
    }

    @Override
    public void delete(Long userId) {
        AssertUtil.assertNull(userId, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.USER_ID_EMPTY);
        });
        this.userInfoMapper.delete(userId);
    }
}
