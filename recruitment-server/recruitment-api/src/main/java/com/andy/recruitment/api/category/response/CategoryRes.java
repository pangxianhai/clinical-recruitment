package com.andy.recruitment.api.category.response;

import java.io.Serializable;
import lombok.Data;

/**
 * 类目信心
 *
 * @author 庞先海 2020-04-12
 */
@Data
public class CategoryRes implements Serializable {

    private static final long serialVersionUID = - 4153680315082133806L;

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
}
