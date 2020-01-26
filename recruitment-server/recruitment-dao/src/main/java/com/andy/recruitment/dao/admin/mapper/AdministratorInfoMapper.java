package com.andy.recruitment.dao.admin.mapper;

import com.andy.recruitment.dao.admin.entity.AdministratorInfoDO;
import com.andy.recruitment.dao.admin.entity.AdministratorQuery;
import com.soyoung.base.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 管理员mapper
 *
 * @author 庞先海 2020-01-26
 */
@Repository
public interface AdministratorInfoMapper {

    /**
     * 管理员分页查询
     *
     * @param query 查询参数
     * @param page  分页参数
     * @return 管理员信息
     */
    List<AdministratorInfoDO> select(AdministratorQuery query, Page page);

    /**
     * 管理员查询
     *
     * @param query 查询参数
     * @return 管理员信息
     */
    List<AdministratorInfoDO> select(AdministratorQuery query);

    /**
     * 更新管理员信息
     *
     * @param administratorInfoDo 管理员信息
     * @return 更新数量
     */
    int update(AdministratorInfoDO administratorInfoDo);

    /**
     * 插入管理员信息
     *
     * @param administratorInfoDo 管理员信息
     * @return 插入数量
     */
    int insert(AdministratorInfoDO administratorInfoDo);
}
