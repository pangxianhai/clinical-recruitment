package com.andy.recruitment.user.constant;

import com.xgimi.commons.base.BaseType;

/**
 * 性别定义
 *
 * @author 庞先海 2018-12-28
 */
public class Gender extends BaseType {

    public static final Gender MALE = new Gender(1, "男");

    public static final Gender FEMALE = new Gender(2, "女");

    public Gender(int code, String desc) {
        super(code, desc);
    }
}
