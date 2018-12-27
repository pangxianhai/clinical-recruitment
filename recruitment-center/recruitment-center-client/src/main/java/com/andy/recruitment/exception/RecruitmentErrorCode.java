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

    ;

    private int code;

    RecruitmentErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
