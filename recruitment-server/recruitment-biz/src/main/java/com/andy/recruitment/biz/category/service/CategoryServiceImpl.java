package com.andy.recruitment.biz.category.service;

import com.andy.recruitment.api.category.response.CategoryDetailRes;
import com.andy.recruitment.biz.category.util.CategoryUtil;
import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.category.dao.CategoryDAO;
import com.andy.recruitment.dao.category.entity.CategoryDO;
import com.andy.recruitment.dao.category.entity.CategoryQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import com.andy.spring.util.asserts.AssertUtil;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 招募项目项目类目服务接口实现
 *
 * @author 庞先海 2020-04-12
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO;

    @Autowired
    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public void addCategory(CategoryDO categoryDo, String operator) {
        CategoryDO sourceCategoryDo = this.categoryDAO.getCategoryByName(categoryDo.getCategoryName());
        AssertUtil.assertTrue(sourceCategoryDo == null, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.CATEGORY_HAS_EXIST);
        });
        this.categoryDAO.addCategory(categoryDo, operator);
    }

    @Override
    public void updateCategory(CategoryDO categoryDo, String operator) {
        CategoryDO sourceCategoryDo = this.categoryDAO.getCategoryByName(categoryDo.getCategoryName());
        boolean validName = sourceCategoryDo == null || sourceCategoryDo.getCategoryId().equals(
            categoryDo.getCategoryId());

        AssertUtil.assertTrue(validName, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.CATEGORY_HAS_EXIST);
        });
        this.categoryDAO.updateCategory(categoryDo, operator);
    }

    @Override
    public CategoryDO getCategoryById(Long categoryId) {
        return this.categoryDAO.getCategoryById(categoryId);
    }

    @Override
    public Map<Long, CategoryDO> getCategoryByIds(List<Long> categoryIdList) {
        List<CategoryDO> categoryDoList = this.categoryDAO.getCategoryByIds(categoryIdList);
        if (CollectionUtils.isEmpty(categoryDoList)) {
            return Collections.emptyMap();
        }
        return categoryDoList.stream().filter(Objects::nonNull).collect(
            Collectors.toMap(CategoryDO::getCategoryId, Function.identity(), (c1, c2) -> c1));
    }

    @Override
    public List<CategoryDetailRes> getCategoryByParentId(Long parentId) {
        if (parentId == null) {
            parentId = CategoryDAO.ROOT_CATEGORY_ID;
        }
        List<CategoryDO> categoryDoList = this.categoryDAO.getCategoryByParentId(parentId);
        List<CategoryDetailRes> categoryDetailResList = CategoryUtil.transformCategoryDetailRes(categoryDoList);
        if (CollectionUtils.isEmpty(categoryDetailResList)) {
            return Collections.emptyList();
        }
        for (CategoryDetailRes categoryDetailRes : categoryDetailResList) {
            List<CategoryDO> children = this.categoryDAO.getCategoryByParentId(categoryDetailRes.getCategoryId());
            categoryDetailRes.setHasNexLevel(CollectionUtils.isNotEmpty(children));
        }
        return categoryDetailResList;
    }

    @Override
    public PageResult<CategoryDO> getCategory(CategoryQuery query, Paginator paginator) {
        return this.categoryDAO.getCategory(query, paginator);
    }

    @Override
    public CategoryDO getCategoryByName(String categoryName) {
        return this.categoryDAO.getCategoryByName(categoryName);
    }
}
