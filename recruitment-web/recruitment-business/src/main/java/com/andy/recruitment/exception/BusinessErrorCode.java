package com.andy.recruitment.exception;

/**
 * 错误码
 *
 * @author 庞先海 2019-01-17
 */
public enum BusinessErrorCode {

    /**
     * 用户不能为空
     */
    USER_NOT_EMPTY(102000),
    /**
     * 验证码错误
     */
    VER_CODE_ERROR(102001),
    /**
     * 未登陆
     */
    LOGIN_NOT_LOGIN(102002),
    /**
     * 地址参数错误
     */
    REGION_ADDRESS_ERROR(102003),
    /**
     * 账户信息异常
     */
    USER_ACCOUNT_ERROR(102004),
    /**
     * 用户不存在
     */
    USER_NOT_EXIST(102005),
    /**
     * 密码错误
     */
    USER_PASSWORD_ERROR(102006),
    /**
     * 用户已经被冻结
     */
    USER_FREEZE(102007),
    /**
     * 该手机号已被注册
     */
    USER_PHONE_HAS_REGISTER(102008),
    /**
     * 该用户已经注册
     */
    USER_HAS_REGISTER(102009),
    /**
     * 非法访问
     */
    ILLEGAL_ACCESS(102010),
    /**
     * 您的操作有误
     */
    OPERATE_ERROR(102011),

    /**
     * 招募信息不能为空
     */
    RECRUITMENT_ADD_FAILED(102100),
    /**
     * 该招募尚未开始
     */
    RECRUITMENT_NOT_BEGIN(102101),
    /**
     * 该招募信息已经结束
     */
    RECRUITMENT_HAS_FINISHED(102102),

    /**
     * 您不是患者不能报名招募
     */
    RECRUITMENT_APPLICATION_USER_ERROR(102200),
    /**
     * 您没有权限查看该报名记录
     */
    RECRUITMENT_APPLICATION_NOT_AUTH(102201),
    ;

    private int code;

    BusinessErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }}
