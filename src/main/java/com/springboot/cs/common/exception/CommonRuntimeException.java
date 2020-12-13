package com.springboot.cs.common.exception;

import com.springboot.cs.common.enums.ExceptionEnum;

/**
 * message:给用户看的
 * reason: 给开发看的
 */
public class CommonRuntimeException extends BaseRuntimeException  {

    public CommonRuntimeException(String reason) {
        super(reason, ExceptionEnum.ExceptionType.COMMON.toString());
    }

    public CommonRuntimeException(String message, String reason) {
        super(message, reason, ExceptionEnum.ExceptionType.COMMON.toString());
    }
}
