package com.andy.recruitment.dao.admin.dao;

import com.andy.recruitment.dao.admin.entity.AdministratorInfoDO;
import com.andy.recruitment.dao.admin.entity.AdministratorQuery;
import com.soyoung.base.page.PageResult;
import com.soyoung.base.page.Paginator;

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
     */
    void addAdministrator(AdministratorInfoDO administratorInfoDo);

    /**
     * 更新管理员信息
     *
     * @param administratorInfoDo 管理员信息
     */
    void updateAdministrator(AdministratorInfoDO administratorInfoDo);

    /**
     * 通过用户名查询管理员信息
     *
     * @param userName 用户名
     * @return 管理员信息
     */
    AdministratorInfoDO getAdministratorInfo(String userName);

    /**
     * 分页查询管理员
     *
     * @param query     查询参数
     * @param paginator 分页参数
     * @return 管理员信息列表
     */
    PageResult<AdministratorInfoDO> listAdministratorInfo(AdministratorQuery query, Paginator paginator);
}
