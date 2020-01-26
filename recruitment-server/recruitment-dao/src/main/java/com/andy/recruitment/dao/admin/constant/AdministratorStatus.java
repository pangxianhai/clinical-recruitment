package com.andy.recruitment.dao.admin.constant;

import com.soyoung.base.type.BaseType;

/**
 * 管理员类型
 *
 * @author 庞先海 2020-01-26
 */
public class AdministratorStatus extends BaseType {

    private static final long serialVersionUID = - 867264061529418353L;

    public static final AdministratorStatus NORMAL = new AdministratorStatus(1, "正常");

    public static final AdministratorStatus FREEZE = new AdministratorStatus(2, "冻结");

    public AdministratorStatus(int code, String desc) {
        super(code, desc);
    }
}
