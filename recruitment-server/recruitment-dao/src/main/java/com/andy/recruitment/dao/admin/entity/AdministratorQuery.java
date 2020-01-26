package com.andy.recruitment.dao.admin.entity;

import com.andy.recruitment.dao.admin.constant.AdminType;
import com.andy.recruitment.dao.admin.constant.AdministratorStatus;
import java.io.Serializable;
import lombok.Data;

/**
 * 管理员查询参数
 *
 * @author 庞先海 2020-01-26
 */
@Data
public class AdministratorQuery implements Serializable {

    private static final long serialVersionUID = 9136925072225303942L;

    /**
     * 管理员id
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 管理员类型
     */
    private AdminType type;

    /**
     * 管理员状态
     */
    private AdministratorStatus status;
}
