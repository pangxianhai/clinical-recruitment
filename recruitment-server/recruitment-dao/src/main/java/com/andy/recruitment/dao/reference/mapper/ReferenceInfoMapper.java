package com.andy.recruitment.dao.reference.mapper;

import com.andy.recruitment.dao.reference.entity.ReferenceInfoDO;
import com.andy.recruitment.dao.reference.entity.ReferenceInfoQuery;
import com.andy.spring.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 推荐人mapper接口
 *
 * @author 庞先海 2020-01-27
 */
@Repository
public interface ReferenceInfoMapper {

    /**
     * 推荐人查询
     *
     * @param query 查询参数
     * @return 推荐人信息
     */
    List<ReferenceInfoDO> select(ReferenceInfoQuery query);

    /**
     * 推荐人分页查询
     *
     * @param query 查询参数
     * @param page  分页信息
     * @return 推荐人信息
     */
    List<ReferenceInfoDO> select(ReferenceInfoQuery query, Page page);

    /**
     * 推荐人信息更新
     *
     * @param referenceInfoDo 推荐人信息
     * @return 更新数量
     */
    int update(ReferenceInfoDO referenceInfoDo);

    /**
     * 插入推荐人
     *
     * @param referenceInfoDo 推荐人信息
     * @return 插入数量
     */
    int insert(ReferenceInfoDO referenceInfoDo);
}
