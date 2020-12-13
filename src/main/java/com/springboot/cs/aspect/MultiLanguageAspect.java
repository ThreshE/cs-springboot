package com.springboot.cs.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.cs.common.bean.BaseBean;
import com.springboot.cs.common.bean.WebResult;
import com.springboot.cs.common.enums.LanguageEnums;
import com.springboot.cs.common.exception.BaseRuntimeException;
import com.springboot.cs.common.exception.CommonRuntimeException;
import com.springboot.cs.common.exception.TipRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class MultiLanguageAspect {

    private static Integer min = 30 * 60 * 1000;


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;


    @Pointcut("execution(* *(..))")
    private void pointCutMethod() {

    }

    /**
     * 定义环绕通知
     * @return
     * @throws Throwable
     */
    @Around("pointCutMethod() and @annotation(multiLanguage)")
    public Object around(ProceedingJoinPoint pjp, MultiLanguage multiLanguage) throws Throwable {
        log.info("TokenAspect 前置拦截启动");

        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if(!(sig instanceof MethodSignature)) {
            log.error("该注解只能适用于方法");
            throw  new RuntimeException("该注解只能适用于方法");
        }

        String method = request.getMethod();

        Object[] args = pjp.getArgs();

        String str = JSON.toJSONString(args[0]);

        BaseBean bean = JSON.parseObject(str,BaseBean.class);

        String language = bean.getLanguage();

        LanguageEnums.ResultMessageEnum success = multiLanguage.success();

        String message = LanguageEnums.ResultMessageEnum.getMessage(success.toString(), language);


        Object proceed = pjp.proceed(args);

        WebResult webResult = (WebResult) proceed;

        if(webResult.getResult()) {
            webResult.setMessage(message);
        }

        return proceed;
    }

    @AfterThrowing(throwing="ex",pointcut="@annotation(multiLanguage)")
    public void AfterThrowing(JoinPoint joinPoint, Throwable ex, MultiLanguage multiLanguage) {
        String message = ex.getMessage();

        Object[] args = joinPoint.getArgs();

        String str = JSON.toJSONString(args[0]);

        BaseBean bean = JSON.parseObject(str,BaseBean.class);

        String language = bean.getLanguage();

        String error =  LanguageEnums.ResultMessageEnum.getMessage(message,language);

        Class<? extends Throwable> aClass = ex.getClass();

        if(aClass.equals(TipRuntimeException.class)) {
            throw new TipRuntimeException(error);
        } else if(aClass.equals(CommonRuntimeException.class)) {
            CommonRuntimeException commonRuntimeException = (CommonRuntimeException)ex;
            String reason = commonRuntimeException.getReason();
            throw new CommonRuntimeException(error,reason);

        }


    }

    @PostConstruct
    public void postConstruct() {
        log.info("Aspect is OK!");
    }



}
