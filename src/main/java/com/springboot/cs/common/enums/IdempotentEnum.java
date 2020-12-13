package com.springboot.cs.common.enums;

public class IdempotentEnum {

    public enum IdempotentType {

        UUID("隨機數"),
        PARAM("参数");

        private String name;

        IdempotentType(String name) {
            this.name = name;
        }
    }
}
