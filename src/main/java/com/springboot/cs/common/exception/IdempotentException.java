package com.springboot.cs.common.exception;

import com.springboot.cs.common.enums.ExceptionEnum;

public class IdempotentException extends BaseRuntimeException{

    public IdempotentException(String reason) {
        super(reason,reason, ExceptionEnum.ExceptionType.IDEMPOTENT.toString());
    }

}
