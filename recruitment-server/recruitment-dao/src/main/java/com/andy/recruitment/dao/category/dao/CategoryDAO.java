package com.andy.recruitment.dao.category.dao;

import com.andy.recruitment.dao.category.entity.CategoryDO;
import com.andy.recruitment.dao.category.entity.CategoryQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.List;

/**
 * 招募项目项目类目 DAO 接口
 *
 * @author 庞先海 2020-04-12
 */
public interface CategoryDAO {

    /**
     * 根类目ID 一级类目的父ID
     */
    Long ROOT_CATEGORY_ID = 0L;

    /**
     * 一级类目值
     */
    Integer LEVEL_ONE_CODE = 1;

    /**
     * 添加类目
     *
     * @param categoryDo 类目信息
     * @param operator   操作人
     */
    void addCategory(CategoryDO categoryDo, String operator);

    /**
     * 更新类目
     *
     * @param categoryDo 类目信息
     * @param operator   操作人
     */
    void updateCategory(CategoryDO categoryDo, String operator);

    /**
     * 通过类目id 获取类目
     *
     * @param categoryId 类目id
     * @return 类目信息
     */
    CategoryDO getCategoryById(Long categoryId);

    /**
     * 通过类目名称查询类目
     *
     * @param categoryName 类目名称
     * @return 类目信息
     */
    CategoryDO getCategoryByName(String categoryName);

    /**
     * 通过父类目id 查询类目
     *
     * @param parentId 父类目id
     * @return 类目信息
     */
    List<CategoryDO> getCategoryByParentId(Long parentId);

    /**
     * 分页查询类目
     *
     * @param query     查询参数
     * @param paginator 分页参数
     * @return 类目信息
     */
    PageResult<CategoryDO> getCategory(CategoryQuery query, Paginator paginator);
}
