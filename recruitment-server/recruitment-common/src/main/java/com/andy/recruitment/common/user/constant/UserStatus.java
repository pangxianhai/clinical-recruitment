package com.andy.recruitment.common.user.constant;

import com.andy.spring.type.BaseType;
import java.util.List;

/**
 * 用户状态
 *
 * @author 庞先海 2018-12-28
 */
public class UserStatus extends BaseType {

    private static final long serialVersionUID = - 7463966522569035635L;

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
