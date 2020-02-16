package com.andy.recruitment.dao.reference.dao;

import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;

/**
 * 推荐人DAO接口
 *
 * @author 庞先海 2020-01-27
 */
public interface ReferenceDAO {

    /**
     * 分页查询推荐人信息
     *
     * @param query     查询参数
     * @param paginator 分页参数
     * @return 推荐人信息
     */
    PageResult<ReferenceInfoDO> getReferenceInfo(ReferenceInfoQuery query, Paginator paginator);

    /**
     * 通过userId查询推荐人信息
     *
     * @param userId 用户ID
     * @return 推荐人信息
     */
    ReferenceInfoDO getReferenceInfoByUserId(Long userId);

    /**
     * 通过推荐人ID查询推荐人信息
     *
     * @param referenceId 推荐人ID
     * @return 推荐人信息
     */
    ReferenceInfoDO getReferenceInfoById(Long referenceId);

    /**
     * 添加推荐人
     *
     * @param referenceInfoDo 推荐人信息
     * @param operator        操作人
     */
    void addReferenceInfo(ReferenceInfoDO referenceInfoDo, String operator);

    /**
     * 更新推荐信息
     *
     * @param referenceInfoDo 推荐人信息
     * @param operator        操作人
     */
    void updateReferenceInfo(ReferenceInfoDO referenceInfoDo, String operator);
}
