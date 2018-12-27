package com.andy.recruitment.exception;

/**
 * 系统错误码
 *
 * @author 庞先海 2018-12-27
 */
public enum RecruitmentErrorCode {
    /**
     * 过多查询结果
     */
    TOO_MANY_RESULT(101000),

    /**
     * 添加医生失败
     */
    DOCTOR_ADD_FAILED(101100),
    /**
     * 医生ID为空
     */
    DOCTOR_ID_EMPTY(101101),
    /**
     * 更新医生失败
     */
    DOCTOR_UPDATE_FAILED(101102),

    /**
     * 添加患者失败
     */
    PATIENT_ADD_FAILED(101200),
    /**
     * 患者ID为空
     */
    PATIENT_ID_EMPTY(101201),
    /**
     * 更新患者失败
     */
    PATIENT_UPDATE_FAILED(101202),

    ;

    private int code;

    RecruitmentErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
