package com.andy.recruitment.exception;

import com.xgimi.commons.exception.CommonException;

/**
 * 业务层错误码
 *
 * @author 庞先海 2019-01-17
 */
public class BusinessException extends CommonException {

    public BusinessException(BusinessErrorCode errorCode) {
        super(errorCode.getCode());
    }

    public BusinessException(BusinessErrorCode errorCode, Object... args) {
        super(errorCode.getCode(), args);
    }

    public BusinessException(BusinessErrorCode errorCode, Throwable e) {
        super(errorCode.getCode(), e);
    }

    public BusinessException(BusinessErrorCode errorCode, Throwable e, Object... args) {
        super(errorCode.getCode(), e, args);
    }
}
