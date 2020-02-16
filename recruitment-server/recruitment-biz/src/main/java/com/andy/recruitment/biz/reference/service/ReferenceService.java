package com.andy.recruitment.biz.reference.service;

import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;

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

}
