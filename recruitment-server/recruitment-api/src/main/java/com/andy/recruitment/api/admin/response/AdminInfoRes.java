package com.andy.recruitment.api.admin.response;

import com.andy.recruitment.common.admin.constant.AdminType;
import com.andy.recruitment.common.admin.constant.AdministratorStatus;
import java.io.Serializable;
import lombok.Data;

/**
 * 管理员信息
 *
 * @author 庞先海 2020-04-11
 */
@Data
public class AdminInfoRes implements Serializable {

    private static final long serialVersionUID = 6311719028883059017L;

    /**
     * 管理员ID
     */
    private Long adminId;

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
