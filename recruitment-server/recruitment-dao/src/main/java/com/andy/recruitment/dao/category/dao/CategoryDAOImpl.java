package com.andy.recruitment.dao.category.dao;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.recruitment.dao.category.entity.CategoryDO;
import com.andy.recruitment.dao.category.entity.CategoryQuery;
import com.andy.recruitment.dao.category.mapper.CategoryMapper;
import com.andy.spring.mybatis.paginator.Page;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.PageUtil;
import com.andy.spring.page.Paginator;
import com.andy.spring.util.CollectionUtil;
import com.andy.spring.util.RandomUtil;
import com.andy.spring.util.asserts.AssertUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 招募项目项目类目 DAO 接口实现
 *
 * @author 庞先海 2020-04-12
 */
@Service
public class CategoryDAOImpl implements CategoryDAO {

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryDAOImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void addCategory(CategoryDO categoryDo, String operator) {
        if (categoryDo.getParentId() == null) {
            categoryDo.setParentId(ROOT_CATEGORY_ID);
        }
        if (ROOT_CATEGORY_ID.equals(categoryDo.getParentId())) {
            categoryDo.setLevel(LEVEL_ONE_CODE);
        } else {
            CategoryDO parentCategoryDo = this.getCategoryById(categoryDo.getParentId());
            AssertUtil.assertNull(parentCategoryDo, () -> {
                throw new RecruitmentException(RecruitmentErrorCode.CATEGORY_PARENT_NOT_EXIST);
            });
            Integer level = parentCategoryDo.getLevel() + 1;
            categoryDo.setLevel(level);
        }
        Long categoryId = this.generateCategoryId(categoryDo.getParentId(), categoryDo.getLevel());
        categoryDo.setCategoryId(categoryId);
        categoryDo.setPath(this.generatePath(categoryDo.getParentId()));
        categoryDo.setCreatedBy(operator);
        categoryDo.setCreatedTime(LocalDateTime.now());
        Exception exception = null;
        int count = 0;
        for (int i = 0; i < 5; ++ i) {
            try {
                count = this.categoryMapper.insert(categoryDo);
                if (count > 0) {
                    exception = null;
                    break;
                }
            } catch (Exception e) {
                exception = e;
                //一般可能是类目ID重复 从新生成id重试 最多重试5次
                categoryDo.setCategoryId(categoryDo.getCategoryId() + RandomUtil.getRandom(1000));
            }
        }
        if (exception != null) {
            throw new RuntimeException(exception);
        }
        if (count <= 0) {
            throw new RecruitmentException(RecruitmentErrorCode.CATEGORY_ADD_FAILED);
        }
    }

    @Override
    public void updateCategory(CategoryDO categoryDo, String operator) {
        CategoryDO sourceCategoryDo = this.getCategoryById(categoryDo.getCategoryId());
        AssertUtil.assertNull(sourceCategoryDo, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.CATEGORY_NOT_EXIST);
        });
        if (! sourceCategoryDo.getParentId().equals(categoryDo.getParentId())) {
            CategoryDO parentCategoryDo = this.getCategoryById(categoryDo.getParentId());
            AssertUtil.assertNull(parentCategoryDo, () -> {
                throw new RecruitmentException(RecruitmentErrorCode.CATEGORY_PARENT_NOT_EXIST);
            });
            Integer level = parentCategoryDo.getLevel() + 1;
            categoryDo.setLevel(level);
            categoryDo.setPath(this.generatePath(categoryDo.getParentId()));
        } else {
            categoryDo.setParentId(null);
            //级别不能随意修改
            categoryDo.setLevel(null);
            //路劲不能随意修改
            categoryDo.setPath(null);
        }
        categoryDo.setId(sourceCategoryDo.getId());
        categoryDo.setUpdatedBy(operator);
        categoryDo.setUpdatedTime(LocalDateTime.now());
        int count = this.categoryMapper.update(categoryDo);
        AssertUtil.assertTrue(count > 0, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.CATEGORY_UPDATE_FAILED);
        });
    }

    @Override
    public CategoryDO getCategoryById(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        CategoryQuery query = new CategoryQuery();
        query.setCategoryId(categoryId);
        List<CategoryDO> categoryDoList = this.categoryMapper.select(query);
        return CollectionUtil.parseOne(categoryDoList, Function.identity());
    }

    @Override
    public CategoryDO getCategoryByName(String categoryName) {
        if (StringUtils.isEmpty(categoryName)) {
            return null;
        }
        CategoryQuery query = new CategoryQuery();
        query.setCategoryName(categoryName);
        List<CategoryDO> categoryDoList = this.categoryMapper.select(query);
        return CollectionUtil.parseOne(categoryDoList, Function.identity());
    }

    @Override
    public List<CategoryDO> getCategoryByParentId(Long parentId) {
        if (parentId == null) {
            return null;
        }
        CategoryQuery query = new CategoryQuery();
        query.setParentId(parentId);
        return this.categoryMapper.select(query);
    }

    @Override
    public PageResult<CategoryDO> getCategory(CategoryQuery query, Paginator paginator) {
        Page page = PageUtil.transformToPage(paginator);
        List<CategoryDO> categoryDoList = this.categoryMapper.select(query, page);
        return new PageResult<>(categoryDoList, PageUtil.transformToPaginator(page));
    }

    private Long generateCategoryId(Long parentId, Integer level) {
        List<CategoryDO> siblingCategoryDo = this.getCategoryByParentId(parentId);
        if (CollectionUtils.isEmpty(siblingCategoryDo)) {
            return level * 10000L;
        }
        List<Long> siblingCategoryIdList = siblingCategoryDo.stream().map(CategoryDO::getCategoryId).filter(
            Objects::nonNull).collect(Collectors.toList());
        long minId = siblingCategoryIdList.get(0);
        long maxId = siblingCategoryIdList.get(0);
        for (Long cid : siblingCategoryIdList) {
            if (cid < minId) {
                minId = cid;
            }
            if (cid > maxId) {
                maxId = cid;
            }
        }
        for (long id = minId + 1; id < maxId; id++) {
            if (siblingCategoryIdList.contains(id)) {
                continue;
            }
            return id;
        }
        return maxId + 1;
    }

    private String generatePath(Long parentId) {
        if (ROOT_CATEGORY_ID.equals(parentId)) {
            return StringUtils.EMPTY;
        }
        StringBuilder pathBuilder = new StringBuilder("/" + parentId);
        do {
            CategoryDO categoryDo = this.getCategoryById(parentId);
            if (categoryDo == null || ROOT_CATEGORY_ID.equals(categoryDo.getParentId())) {
                break;
            }
            pathBuilder.insert(0, "/" + categoryDo.getParentId());
            parentId = categoryDo.getParentId();
        } while (true);
        return pathBuilder.toString();
    }
}
