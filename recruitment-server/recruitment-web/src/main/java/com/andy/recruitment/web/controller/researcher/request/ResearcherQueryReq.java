package com.andy.recruitment.web.controller.researcher.request;

import com.andy.recruitment.dao.user.constant.UserStatus;
import com.soyoung.base.base.BaseQueryReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private String realName;
    /**
     * 手机号码模糊查询
     */
    private String phoneLike;
    /**
     * 用户状态
     */
    private UserStatus status;
}
