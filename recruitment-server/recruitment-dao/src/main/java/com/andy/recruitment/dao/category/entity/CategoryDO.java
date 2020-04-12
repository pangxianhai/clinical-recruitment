package com.andy.recruitment.dao.category.entity;

import com.andy.spring.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 招募项目项目类目
 *
 * @author 庞先海
 * @since 2020-04-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CategoryDO extends BaseDO {

    private static final long serialVersionUID = - 6541841241964613140L;

    /**
     * 类目ID
     */
    private Long categoryId;

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

    /**
     * 类目路径,从一级类目到本级所有父类目列表 不包含本级类目id
     */
    private String path;
}
