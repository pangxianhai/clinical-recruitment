package com.andy.recruitment.biz.reference.service;

import com.andy.recruitment.api.reference.response.ReferenceDetailInfoRes;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.List;
import java.util.Map;

/**
 * 推荐人服务
 *
 * @author 庞先海 2020-02-15
 */
public interface ReferenceService {

    /**
     * 推荐人人注册
     *
     * @param referenceInfoDo 推荐人信息
     * @param userInfoDo      用户信息
     * @param operator        操作人
     */
    void registerReference(ReferenceInfoDO referenceInfoDo, UserInfoDO userInfoDo, String operator);

    /**
     * 更新推荐人信息
     *
     * @param referenceInfoDo 推荐人信息
     * @param userInfoDo      用户信息
     * @param operator        操作人
     */
    void updateReference(ReferenceInfoDO referenceInfoDo, UserInfoDO userInfoDo, String operator);

    /**
     * 分页查询推荐人信息
     *
     * @param query     查询参数
     * @param paginator 分页参数
     * @return 推荐人信息
     */
    PageResult<ReferenceInfoDO> getReference(ReferenceInfoQuery query, Paginator paginator);

    /**
     * 通过推荐人ID查询推荐人
     *
     * @param referenceId 推荐人ID
     * @return 推荐人信息
     */
    ReferenceInfoDO getReference(Long referenceId);

    /**
     * 通过用户ID查询推荐人信息
     *
     * @param userId 用户ID
     * @return 推荐人信息
     */
    ReferenceInfoDO getReferenceByUserId(Long userId);


    /**
     * 通过userId列表查询推荐人信息
     *
     * @param userIdList 用户 ID 列表
     * @return 推荐人信息
     */
    List<ReferenceInfoDO> getReferenceInfoByUserIdList(List<Long> userIdList);


    /**
     * 通过userId列表查询推荐人信息
     *
     * @param userIdList 用户 ID 列表
     * @return 推荐人信息
     */
    Map<Long, ReferenceDetailInfoRes> getReferenceDetailInfoRes(List<Long> userIdList);

    /**
     * 通过手机号查询推荐人信息
     *
     * @param phone 手机号
     * @return 推荐人信息
     */
    ReferenceInfoDO getReferenceByPhone(String phone);

}
