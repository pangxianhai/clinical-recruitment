package com.andy.recruitment.common.exception;

import com.andy.spring.exception.CommonException;

/**
 * 系统异常
 *
 * @author 庞先海 2018-12-27
 */
public class RecruitmentException extends CommonException {

    private static final long serialVersionUID = 5359576479549248628L;

    public RecruitmentException(RecruitmentErrorCode errorCode) {
        super(errorCode.getCode());
    }

    public RecruitmentException(RecruitmentErrorCode errorCode, Object... args) {
        super(errorCode.getCode(), args);
    }

    public RecruitmentException(RecruitmentErrorCode errorCode, Throwable e) {
        super(errorCode.getCode(), e);
    }

    public RecruitmentException(RecruitmentErrorCode errorCode, Throwable e, Object... args) {
        super(errorCode.getCode(), e, args);
    }
}
