package com.andy.recruitment.dao.category.mapper;

import com.andy.recruitment.dao.category.entity.CategoryDO;
import com.andy.recruitment.dao.category.entity.CategoryQuery;
import com.andy.spring.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 招募项目项目类目 mapper 接口
 *
 * @author 庞先海 2020-04-12
 */
@Repository
public interface CategoryMapper {

    /**
     * 分页查询类目
     *
     * @param query 查询参数
     * @param page  分页参数
     * @return 类目列表
     */
    List<CategoryDO> select(CategoryQuery query, Page page);

    /**
     * 查询类目
     *
     * @param query 查询参数
     * @return 类目列表
     */
    List<CategoryDO> select(CategoryQuery query);

    /**
     * 更新类目信息
     *
     * @param categoryDo 类目信息
     * @return 更新数量
     */
    int update(CategoryDO categoryDo);

    /**
     * 插入类目
     *
     * @param categoryDo 类目信息
     * @return 插入数量
     */
    int insert(CategoryDO categoryDo);
}
