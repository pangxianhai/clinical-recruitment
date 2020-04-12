package com.andy.recruitment.api.category.request;

import java.io.Serializable;
import lombok.Data;

/**
 * 类目添加参数
 *
 * @author 庞先海 2020-04-12
 */
@Data
public class CategoryAddReq implements Serializable {

    private static final long serialVersionUID = 1657637757173140952L;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 父级ID
     */
    private Long parentId;
}
