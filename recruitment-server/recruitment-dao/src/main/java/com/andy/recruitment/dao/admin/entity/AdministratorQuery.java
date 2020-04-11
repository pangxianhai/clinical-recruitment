package com.andy.recruitment.dao.admin.entity;

import com.andy.recruitment.common.admin.constant.AdminType;
import com.andy.recruitment.common.admin.constant.AdministratorStatus;
import java.io.Serializable;
import java.util.List;
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
     * 管理员ID
     */
    private Long adminId;

    /**
     * 管理员用户ID
     */
    private Long userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 手机号模糊查询
     */
    private String phoneLike;

    /**
     * 用户ID列表
     */
    private List<Long> userIdList;

    /**
     * 管理员类型
     */
    private AdminType type;

    /**
     * 管理员状态
     */
    private AdministratorStatus status;
}
