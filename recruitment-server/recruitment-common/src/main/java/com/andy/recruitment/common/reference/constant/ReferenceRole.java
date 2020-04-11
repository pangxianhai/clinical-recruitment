package com.andy.recruitment.common.reference.constant;

import com.andy.spring.type.BaseType;
import java.util.List;

/**
 * 推荐人角色
 *
 * @author 庞先海 2020-04-06
 */
public class ReferenceRole extends BaseType {

    private static final long serialVersionUID = 5964545699751411662L;

    public static final ReferenceRole HEADS_OF_DEPARTMENT = new ReferenceRole(1, "研究员");

    public static final ReferenceRole DOCTOR = new ReferenceRole(2, "医生");

    public ReferenceRole(int code, String desc) {
        super(code, desc);
    }

    public static List<ReferenceRole> getValues() {
        return BaseType.getValues(ReferenceRole.class);
    }

    public static ReferenceRole parse(Integer code) {
        return BaseType.parse(ReferenceRole.getValues(), code);
    }

}
