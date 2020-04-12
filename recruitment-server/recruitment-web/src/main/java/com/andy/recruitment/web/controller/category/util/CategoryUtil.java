package com.andy.recruitment.web.controller.category.util;

import com.andy.recruitment.api.category.request.CategoryAddReq;
import com.andy.recruitment.api.category.request.CategoryQueryReq;
import com.andy.recruitment.api.category.response.CategoryRes;
import com.andy.recruitment.dao.category.entity.CategoryDO;
import com.andy.recruitment.dao.category.entity.CategoryQuery;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

/**
 * 类目工具
 *
 * @author 庞先海 2020-04-12
 */
public class CategoryUtil {

    public static CategoryQuery transformCategoryQuery(CategoryQueryReq queryReq) {
        CategoryQuery query = new CategoryQuery();
        BeanUtils.copyProperties(queryReq, query);
        return query;
    }

    public static CategoryRes transformCategoryRes(CategoryDO categoryDo) {
        if (categoryDo == null) {
            return null;
        }
        CategoryRes categoryRes = new CategoryRes();
        BeanUtils.copyProperties(categoryDo, categoryRes);
        return categoryRes;
    }

    public static List<CategoryRes> transformCategoryRes(List<CategoryDO> categoryDoList) {
        if (CollectionUtils.isEmpty(categoryDoList)) {
            return Collections.emptyList();
        }
        return categoryDoList.stream().map(CategoryUtil::transformCategoryRes).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    public static CategoryDO transformCategoryDo(CategoryAddReq categoryAddReq) {
        CategoryDO categoryDo = new CategoryDO();
        BeanUtils.copyProperties(categoryAddReq, categoryDo);
        return categoryDo;
    }
}
