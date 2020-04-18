package com.andy.recruitment.api.category.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类目信息
 *
 * @author 庞先海 2020-04-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryDetailRes extends CategoryRes {

    private static final long serialVersionUID = 5868018819224000404L;

    /**
     * 是否有下一级
     */
    private Boolean hasNexLevel;
}
