package com.andy.recruitment.exception;

import com.xgimi.commons.exception.CommonException;

/**
 * 系统异常
 *
 * @author 庞先海 2018-12-27
 */
public class RecruitmentException extends CommonException {
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
