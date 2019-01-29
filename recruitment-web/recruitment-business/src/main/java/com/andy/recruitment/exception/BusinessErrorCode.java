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
     * 招募信息不能为空
     */
    RECRUITMENT_ADD_FAILE(102100),

    ;

    private int code;

    BusinessErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }}
