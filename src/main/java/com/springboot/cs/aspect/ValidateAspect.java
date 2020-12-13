package com.springboot.cs.aspect;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.springboot.cs.common.exception.BaseRuntimeException;
import com.springboot.cs.common.exception.CommonRuntimeException;
import com.springboot.cs.common.utils.ListUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
@Aspect
@Component
@Order(1)
public class ValidateAspect {

    @Pointcut("execution(* *(..))")
    private void pointCutMethod() {

    }

    @Around("pointCutMethod() and @annotation(validate)")
    public Object around(ProceedingJoinPoint pjp, Validate validate) throws CommonRuntimeException,Throwable {

        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;

        Class[] parameterTypes = msig.getParameterTypes();

        Object[] args = pjp.getArgs();

        System.out.println(JSON.toJSONString(args));
        Class clz = parameterTypes[0];
        String[] params = validate.params();

        if(clz == null) {
            throw new CommonRuntimeException("注解格式不正确");
        }

        if(params == null) {
            return pjp.proceed(args);
        }

//        Object obj = clz.newInstance();

        for (String param : params) {
            Method method = getMethod(clz, param);

            Object invoke = method.invoke(args[0]);

            Class<?> paramType = method.getReturnType();
            if(paramType.equals(String.class)) {
                String str = (String) invoke;
                if(StringUtils.isEmpty(str)) {
                    throw new CommonRuntimeException(param + "不能为空");
                }
            } else if(paramType.equals(List.class)) {
                List list = (List) invoke;
                if(ListUtil.isNull(list)) {
                    throw new CommonRuntimeException(param + "不能为空");
                }
            } else if(param.equals(Long.class)) {
                Long l = (Long) invoke;
                if(l == null) {
                    throw new CommonRuntimeException(param + "不能为空");
                }
            } else if(param.equals(Integer.class)) {
                Integer i = (Integer) invoke;
                if(i == null) {
                    throw new CommonRuntimeException(param + "不能为空");
                }
            } else if(param.equals(Boolean.class)) {
                Boolean b = (Boolean) invoke;
                if(b == null) {
                    throw new CommonRuntimeException(param + "不能为空");
                }
            }


        }


        return pjp.proceed(args);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("Validate is pass!");
    }

    private Method getMethod(Class clz,String fieldName) {
        StringBuffer sb = new StringBuffer();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        try {
            return clz.getMethod(sb.toString());
        } catch (Exception e) {
        }
        return null;
    }
}
