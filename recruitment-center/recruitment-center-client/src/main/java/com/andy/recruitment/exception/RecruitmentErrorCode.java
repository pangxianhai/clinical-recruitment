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

    /**
     * 添加用户失败
     */
    USER_ADD_FAILED(101300),
    /**
     * 用户ID为空
     */
    USER_ID_EMPTY(101301),
    /**
     * 更新用户失败
     */
    USER_UPDATE_FAILED(101302),

    /**
     * 招募信息添加失败
     */
    RECRUITMENT_ADD_FAILE(101400),
    /**
     * 招募信息更新失败
     */
    RECRUITMENT_UPDATE_FAILE(101401),
    /**
     * 招募信息ID为空
     */
    RECRUITMENT_ID_EMPTY(101402),

    /**
     * 研究中心添加失败
     */
    RESEARCH_CENTER_ADD_FAILED(101500),
    /**
     * 研究中心更新失败
     */
    RESEARCH_CENTER_UPDATE_FAILED(101501),
    /**
     * 研究中心ID为空
     */
    RESEARCH_CENTER_ID_EMPTY(101502),
    /**
     * 研究中心删除失败
     */
    RESEARCH_CENTER_DELETE_FAILED(101503),

    ;

    private int code;

    RecruitmentErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }}
