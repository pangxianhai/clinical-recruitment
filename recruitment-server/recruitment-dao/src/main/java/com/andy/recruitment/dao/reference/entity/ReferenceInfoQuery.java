package com.andy.recruitment.dao.reference.entity;

import com.andy.recruitment.dao.user.constant.UserStatus;
import java.io.Serializable;
import lombok.Data;

/**
 * 推荐人查询参数
 *
 * @author 庞先海 2020-01-27
 */
@Data
public class ReferenceInfoQuery implements Serializable {

    private static final long serialVersionUID = - 2442097856732489920L;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 省ID
     */
    private Long provinceId;
    /**
     * 城市ID
     */
    private Long cityId;
    /**
     * 区ID
     */
    private Long districtId;
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