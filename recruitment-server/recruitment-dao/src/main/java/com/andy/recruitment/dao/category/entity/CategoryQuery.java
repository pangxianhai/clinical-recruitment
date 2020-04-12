package com.andy.recruitment.dao.category.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 招募项目项目类目查询参数
 *
 * @author 庞先海 2020-04-12
 */
@Data
public class CategoryQuery implements Serializable {

    private static final long serialVersionUID = 1458732197779565312L;

    /**
     * 类目ID
     */
    private Long categoryId;

    /**
     * 类目名称
     */
    private String categoryNameLike;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 类目级别
     */
    private Integer level;

    /**
     * 父级ID
     */
    private Long parentId;
}
