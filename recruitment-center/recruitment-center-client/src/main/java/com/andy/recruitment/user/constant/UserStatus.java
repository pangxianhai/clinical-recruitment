package com.andy.recruitment.user.constant;

import com.xgimi.commons.base.BaseType;
import java.util.List;

/**
 * 用户状态
 *
 * @author 庞先海 2018-12-28
 */
public class UserStatus extends BaseType {

    public static final UserStatus NORMAL = new UserStatus(1, "正常");

    public static final UserStatus FREEZE = new UserStatus(2, "冻结");

    public UserStatus(int code, String desc) {
        super(code, desc);
    }

    public static List<UserStatus> getValues() {
        return BaseType.getValues(UserStatus.class);
    }

    public static UserStatus parse(Integer code) {
        return BaseType.parse(UserStatus.getValues(), code);
    }
}
