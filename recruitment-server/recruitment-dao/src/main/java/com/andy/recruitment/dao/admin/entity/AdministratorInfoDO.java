package com.andy.recruitment.dao.admin.entity;

import com.andy.recruitment.common.admin.constant.AdminType;
import com.andy.recruitment.common.admin.constant.AdministratorStatus;
import com.andy.spring.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 管理员信息
 *
 * @author 庞先海 2020-01-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AdministratorInfoDO extends BaseDO {

    private static final long serialVersionUID = - 3541741649351158774L;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 管理员类型
     */
    private AdminType type;

    /**
     * 管理员状态
     */
    private AdministratorStatus status;
}
