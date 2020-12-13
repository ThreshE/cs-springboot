package com.springboot.cs.common.enums;

public class ExceptionEnum {

    public enum ExceptionType {
        COMMON("普通异常"),
        TIP("系统提示"),
        IDEMPOTENT("幂等异常");

        private String message;;

        ExceptionType(String message) {
            this.message = message;
        }
    }
}
