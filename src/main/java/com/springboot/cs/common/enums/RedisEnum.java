package com.springboot.cs.common.enums;

public class RedisEnum {

    public enum RedisType {
        IDEMPOTENT("幂等");

        private String message;

        RedisType(String message) {
            this.message = message;
        }
    }
}
