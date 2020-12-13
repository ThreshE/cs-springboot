package com.springboot.cs.aspect;

import com.springboot.cs.common.enums.IdempotentEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    IdempotentEnum.IdempotentType type() default IdempotentEnum.IdempotentType.PARAM;

    int timeout();
}
