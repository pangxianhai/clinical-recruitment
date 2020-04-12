package com.andy.recruitment.api.category.request;

import com.andy.spring.base.BaseQueryReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类目查询参数
 *
 * @author 庞先海 2020-04-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryQueryReq extends BaseQueryReq {

    private static final long serialVersionUID = - 1441432621554063488L;

    /**
     * 类目ID
     */
    private Long categoryId;

    /**
     * 类目名称
     */
    private String categoryNameLike;

    /**
     * 类目级别
     */
    private Integer level;

    /**
     * 父级ID
     */
    private Long parentId;
}
