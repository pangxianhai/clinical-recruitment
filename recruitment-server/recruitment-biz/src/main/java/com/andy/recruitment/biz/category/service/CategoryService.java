package com.andy.recruitment.biz.category.service;

import com.andy.recruitment.api.category.response.CategoryDetailRes;
import com.andy.recruitment.dao.category.entity.CategoryDO;
import com.andy.recruitment.dao.category.entity.CategoryQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.List;
import java.util.Map;

/**
 * 招募项目项目类目服务接口
 *
 * @author 庞先海 2020-04-12
 */
public interface CategoryService {

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
     * 批量查询类目
     *
     * @param categoryIdList 类目id列表
     * @return 类目信息
     */
    Map<Long, CategoryDO> getCategoryByIds(List<Long> categoryIdList);

    /**
     * 通过父类目id 查询类目 当 parentId 为空时返回所有一级类目
     *
     * @param parentId 父类目id
     * @return 类目信息
     */
    List<CategoryDetailRes> getCategoryByParentId(Long parentId);

    /**
     * 分页查询类目
     *
     * @param query     查询参数
     * @param paginator 分页参数
     * @return 类目信息
     */
    PageResult<CategoryDO> getCategory(CategoryQuery query, Paginator paginator);

    /**
     * 通过类目名称查询类目
     *
     * @param categoryName 类目名称
     * @return 类目信息
     */
    CategoryDO getCategoryByName(String categoryName);
}
