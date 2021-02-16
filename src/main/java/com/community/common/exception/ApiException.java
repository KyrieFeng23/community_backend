package com.community.common.exception;

import com.community.common.api.IErrorCode;

/**
 * Description:自定义API异常
 *
 * @author fyf
 * @since 2021/2/16 9:38 下午
 */
public class ApiException extends RuntimeException {
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
