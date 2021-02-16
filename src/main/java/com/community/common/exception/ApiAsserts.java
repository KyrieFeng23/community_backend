package com.community.common.exception;

import com.community.common.api.IErrorCode;

/**
 * Description:断言处理类，抛出自定义异常
 *
 * @author fyf
 * @since 2021/2/16 9:32 下午
 */


public class ApiAsserts {
    /**
     * 抛失败异常
     *
     * @param message 说明
     */
    public static void fail(String message) {
        throw new ApiException(message);
    }

    /**
     * 抛失败异常
     *
     * @param errorCode 状态码
     */
    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
