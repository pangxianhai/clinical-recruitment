package com.andy.recruitment.common.admin.constant;

import com.andy.spring.type.BaseType;

/**
 * 管理员类型
 *
 * @author 庞先海 2020-01-26
 */
public class AdminType extends BaseType {

    private static final long serialVersionUID = - 5605598653571759619L;

    public static final AdminType SYS_ADMIN = new AdminType(1, "系统管理员");

    public static final AdminType BUSINESS_ADMIN = new AdminType(2, "业务管理员");

    public AdminType(int code, String desc) {
        super(code, desc);
    }
}
