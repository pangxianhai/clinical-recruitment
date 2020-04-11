package com.andy.recruitment.dao.admin.dao;

import com.andy.recruitment.dao.admin.entity.AdministratorInfoDO;
import com.andy.recruitment.dao.admin.entity.AdministratorQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;

/**
 * 管理员DAO
 *
 * @author 庞先海 2020-01-26
 */
public interface AdministratorDAO {

    /**
     * 添加管理员
     *
     * @param administratorInfoDo 管理员信息
     * @param operator            操作人
     */
    void addAdministrator(AdministratorInfoDO administratorInfoDo, String operator);

    /**
     * 更新管理员信息
     *
     * @param administratorInfoDo 管理员信息
     * @param operator            操作人
     */
    void updateAdministrator(AdministratorInfoDO administratorInfoDo, String operator);

    /**
     * 通过userId查询管理员信息
     *
     * @param userId 用户ID
     * @return 管理员信息
     */
    AdministratorInfoDO getAdministratorByUserId(Long userId);

    /**
     * 通过管理员ID查询管理员信息
     *
     * @param adminId 用户ID
     * @return 管理员信息
     */
    AdministratorInfoDO getAdministratorById(Long adminId);

    /**
     * 分页查询管理员
     *
     * @param query     查询参数
     * @param paginator 分页参数
     * @return 管理员信息列表
     */
    PageResult<AdministratorInfoDO> listAdministratorInfo(AdministratorQuery query, Paginator paginator);
}
