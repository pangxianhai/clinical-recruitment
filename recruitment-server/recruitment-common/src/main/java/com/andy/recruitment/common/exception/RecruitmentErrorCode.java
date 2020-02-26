package com.andy.recruitment.common.exception;

/**
 * 系统错误码
 *
 * @author 庞先海 2018-12-27
 */
public enum RecruitmentErrorCode {
    /**
     * 推荐人注册失败
     */
    REFERENCE_ADD_FAILED(101100),
    /**
     * 推荐人ID为空
     */
    REFERENCE_ID_EMPTY(101101),
    /**
     * 更新推荐人信息失败
     */
    REFERENCE_UPDATE_FAILED(101102),
    /**
     * 推荐人不存在
     */
    REFERENCE_NOT_EXIST(101103),
    /**
     * 地区信息错误
     */
    REFERENCE_REGISTER_REGION_ERROR(101104),

    /**
     * 注册患者失败
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
     * 患者不存在
     */
    PATIENT_NOT_EXIST(101203),
    /**
     * 地区信息错误
     */
    PATIENT_REGISTER_REGION_ERROR(101204),

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
     * 请保证一个管理员
     */
    USER_MANAGER_MUST_ONE(101303),
    /**
     * 手机号已经被使用
     */
    USER_PHONE_HAS_USED(101304),
    /**
     * 未登录
     */
    USER_NOT_LOGIN(101305),
    /**
     * 用户注册失败
     */
    USER_REGISTER_FAILED(101306),

    /**
     * 招募信息添加失败
     */
    RECRUITMENT_ADD_FAILED(101400),
    /**
     * 招募信息更新失败
     */
    RECRUITMENT_UPDATE_FAILED(101401),
    /**
     * 招募信息ID为空
     */
    RECRUITMENT_ID_EMPTY(101402),
    /**
     * 招募项目添加研究机构失败
     */
    RECRUITMENT_ORGANIZATION_ADD_FAILED(101420),
    /**
     * 招募项目删除研究机构ID为空
     */
    RECRUITMENT_ORGANIZATION_DELETE_ID_EMPTY(101421),
    /**
     * 招募项目删除研究机构失败
     */
    RECRUITMENT_ORGANIZATION_DELETE_FAILED(101422),

    /**
     * 机构信息添加失败
     */
    ORGANIZATION_ADD_FAILED(101500),
    /**
     * 机构信息更新失败
     */
    ORGANIZATION_UPDATE_FAILED(101501),
    /**
     * 机构ID为空
     */
    ORGANIZATION_ID_EMPTY(101502),
    /**
     * 机构科室添加失败
     */
    ORGANIZATION_DEPARTMENT_ADD_FAILED(101510),
    /**
     * 机构科室更新失败
     */
    ORGANIZATION_DEPARTMENT_UPDATE_FAILED(101511),
    /**
     * 机构科室ID为空
     */
    ORGANIZATION_DEPARTMENT_ID_EMPTY(101512),

    /**
     * 招募信息申请失败
     */
    RECRUITMENT_APPLICATION_ADD_FAILED(101600),
    /**
     * 招募申请信息更新失败
     */
    RECRUITMENT_APPLICATION_UPDATE_FAILED(101601),
    /**
     * 招募申请信息ID为空
     */
    RECRUITMENT_APPLICATION_ID_EMPTY(101602),
    /**
     * 您已经申请该招募信息无需重复申请
     */
    RECRUITMENT_APPLICATION_HAS_APPLICATION(101603),

    /**
     * 管理员添加失败
     */
    ADMINISTRATOR_ADD_FAILED(101700),
    /**
     * 管理员更新失败
     */
    ADMINISTRATOR_UPDATE_FAILED(101701),
    /**
     * 管理员ID为空
     */
    ADMINISTRATOR_ID_EMPTY(101702),
    /**
     * 用户不存在
     */
    ADMINISTRATOR_USER_NOT_EXIST(101703),
    /**
     * 密码不正确
     */
    ADMINISTRATOR_PASSWORD_ERROR(101704),
    /**
     * 该用户被冻结
     */
    ADMINISTRATOR_FREEZE(101705),
    /**
     * 管理员账户异常
     */
    ADMINISTRATOR_ACCOUNT_ERROR(101706),

    /**
     * 研究员添加失败
     */
    RESEARCHER_ADD_FAILED(101800),
    /**
     * 研究员更新失败
     */
    RESEARCHER_UPDATE_FAILED(101801),
    /**
     * 研究员ID为空
     */
    RESEARCHER_ID_EMPTY(101802),
    /**
     * 研究员不存在
     */
    RESEARCHER_NOT_EXIST(101803),

    ;

    private int code;

    RecruitmentErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
