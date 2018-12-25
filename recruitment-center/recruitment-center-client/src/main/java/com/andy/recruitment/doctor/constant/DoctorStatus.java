package com.andy.recruitment.doctor.constant;


import com.xgimi.commons.base.BaseType;

/**
 * 医生状态定义
 *
 * @author 庞先海 2018-12-25
 */
public class DoctorStatus extends BaseType {

    public final static DoctorStatus NORMAL = new DoctorStatus(1, "正常");

    public final static DoctorStatus FREEZE = new DoctorStatus(2, "冻结");

    public DoctorStatus(int code, String desc) {
        super(code, desc);
    }
}
