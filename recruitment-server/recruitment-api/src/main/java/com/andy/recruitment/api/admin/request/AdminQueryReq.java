package com.andy.recruitment.api.admin.request;

import com.andy.spring.base.BaseQueryReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理员查询信息
 *
 * @author 庞先海 2020-04-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminQueryReq extends BaseQueryReq {

    private static final long serialVersionUID = 1579983041403583229L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号模糊查询
     */
    private String phoneLike;

    /**
     * 管理员类型
     */
    private Integer type;

    /**
     * 管理员状态
     */
    private Integer status;
}
