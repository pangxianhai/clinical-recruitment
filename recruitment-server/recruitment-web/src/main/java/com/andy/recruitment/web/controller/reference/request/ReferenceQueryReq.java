package com.andy.recruitment.web.controller.reference.request;

import com.soyoung.base.base.BaseQueryReq;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 推荐人查询参数
 *
 * @author 庞先海 2020-02-15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReferenceQueryReq extends BaseQueryReq implements Serializable {

    private static final long serialVersionUID = - 2635117696964106247L;

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
     * 推荐人状态
     */
    private Integer status;
}
