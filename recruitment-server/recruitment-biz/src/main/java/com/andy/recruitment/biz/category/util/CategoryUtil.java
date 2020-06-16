package com.andy.recruitment.biz.category.util;

import com.andy.recruitment.api.category.response.CategoryDetailRes;
import com.andy.recruitment.api.category.response.CategoryRes;
import com.andy.recruitment.dao.category.entity.CategoryDO;
import com.andy.spring.util.StringUtil;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * 类目工具
 *
 * @author 庞先海 2020-04-12
 */
public class CategoryUtil {

    public static CategoryDetailRes transformCategoryDetailRes(CategoryDO categoryDo) {
        if (categoryDo == null) {
            return null;
        }
        CategoryDetailRes categoryDetailRes = new CategoryDetailRes();
        BeanUtils.copyProperties(categoryDo, categoryDetailRes);
        categoryDetailRes.setPath(parsePath(categoryDo.getPath()));
        return categoryDetailRes;
    }

    public static List<CategoryDetailRes> transformCategoryDetailRes(List<CategoryDO> categoryDoList) {
        if (CollectionUtils.isEmpty(categoryDoList)) {
            return Collections.emptyList();
        }
        return categoryDoList.stream().map(CategoryUtil::transformCategoryDetailRes).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    public static CategoryRes transformCategoryRes(CategoryDO categoryDo) {
        if (categoryDo == null) {
            return null;
        }
        CategoryRes categoryRes = new CategoryRes();
        BeanUtils.copyProperties(categoryDo, categoryRes);
        categoryRes.setPath(parsePath(categoryDo.getPath()));
        return categoryRes;
    }

    private static List<Long> parsePath(String path) {
        if (StringUtils.isEmpty(path)) {
            return Collections.emptyList();
        }
        List<Long> pathList = StringUtil.split(path, "/", Long.class);
        if (CollectionUtils.isEmpty(pathList)) {
            return Collections.emptyList();
        }
        return pathList.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

}
