package com.andy.recruitment.user.constant;

import com.xgimi.commons.base.BaseType;
import java.util.List;

/**
 * 用户类型
 *
 * @author 庞先海 2018-12-28
 */
public class UserType extends BaseType {

    public static final UserType ADMIN = new UserType(1, "管理员");

    public static final UserType DOCTOR = new UserType(2, "医生");

    public static final UserType PATIENT = new UserType(3, "患者");

    public UserType(int code, String desc) {
        super(code, desc);
    }

    public static List<UserType> getValues() {
        return BaseType.getValues(UserType.class);
    }

    public static UserType parse(Integer code) {
        return BaseType.parse(UserType.getValues(), code);
    }
}
