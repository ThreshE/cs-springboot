package com.springboot.cs.aspect;

import com.springboot.cs.common.enums.LanguageEnums;

import java.lang.annotation.Retention;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MultiLanguage {

    LanguageEnums.ResultMessageEnum success() ;
}
