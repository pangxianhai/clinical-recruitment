package com.andy.recruitment.api.researcher.request;

import com.andy.spring.base.BaseQueryReq;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 研究员查询参数
 *
 * @author 庞先海 2020-01-31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResearcherQueryReq extends BaseQueryReq {

    private static final long serialVersionUID = - 5870447433726844909L;

    /**
     * 真实姓名
     */
    @Length(max = 64)
    private String realName;
    /**
     * 手机号码模糊查询
     */
    @Length(max = 32)
    private String phoneLike;
    /**
     * 研究员状态
     */
    private Integer status;
}
