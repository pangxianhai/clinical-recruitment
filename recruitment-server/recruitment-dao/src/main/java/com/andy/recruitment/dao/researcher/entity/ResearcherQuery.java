package com.andy.recruitment.dao.researcher.entity;

import com.andy.recruitment.dao.user.constant.UserStatus;
import java.io.Serializable;
import lombok.Data;

/**
 * 研究员查询参数
 *
 * @author 庞先海 2020-01-27
 */
@Data
public class ResearcherQuery implements Serializable {

    private static final long serialVersionUID = 9053109323839786038L;

    /**
     * 用户ID
     */
    private Long userId;
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
