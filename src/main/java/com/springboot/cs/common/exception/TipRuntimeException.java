package com.springboot.cs.common.exception;

import com.springboot.cs.common.enums.ExceptionEnum;

public class TipRuntimeException extends BaseRuntimeException {


    public TipRuntimeException(String reason) {
        super(reason,reason, ExceptionEnum.ExceptionType.TIP.toString());
    }

}
