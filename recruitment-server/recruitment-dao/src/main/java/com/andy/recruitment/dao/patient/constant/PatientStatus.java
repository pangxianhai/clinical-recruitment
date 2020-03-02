package com.andy.recruitment.dao.patient.constant;

import com.andy.spring.type.BaseType;
import java.util.List;

/**
 * 患者状态
 *
 * @author 庞先海 2020-02-16
 */
public class PatientStatus extends BaseType {

    private static final long serialVersionUID = - 8373825389012919076L;

    public static final PatientStatus NORMAL = new PatientStatus(1, "正常");

    public static final PatientStatus FREEZE = new PatientStatus(2, "冻结");

    public PatientStatus(int code, String desc) {
        super(code, desc);
    }

    public static List<PatientStatus> getValues() {
        return BaseType.getValues(PatientStatus.class);
    }

    public static PatientStatus parse(Integer code) {
        return BaseType.parse(PatientStatus.getValues(), code);
    }

}
