package com.springboot.cs.aspect;

import com.alibaba.fastjson.JSON;
import com.springboot.cs.common.bean.BaseBean;
import com.springboot.cs.common.enums.IdempotentEnum;
import com.springboot.cs.common.enums.RedisEnum;
import com.springboot.cs.common.exception.IdempotentException;
import com.springboot.cs.common.utils.DateUtil;
import com.springboot.cs.common.utils.JedisUtil;
import com.springboot.cs.common.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Aspect
@Slf4j
@Component
public class IdempotentAspect {

    //类.方法_token_类型(PARAM/UUID)_内容
    private static String key = RedisEnum.RedisType.IDEMPOTENT.toString() + "_%s_%s_%s_%s";

    @Autowired
    private JedisUtil jedisUtil;

    @Pointcut("execution(* *(..))")
    private void pointCutMethod() {

    }

    @Around("pointCutMethod() && @annotation(idempotent)")
    public Object aroundMethod(ProceedingJoinPoint pjp, Idempotent idempotent) throws IdempotentException,Throwable {

        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;

        String clzName = pjp.getTarget().getClass().getName();
        String methodName = msig.getName();
        String targetName = clzName + "." + methodName;

        Object[] args = pjp.getArgs();

        String token = "token";

        //获取注解信息
        IdempotentEnum.IdempotentType type = idempotent.type();
        Integer timeout = idempotent.timeout();

        //校验
        String idempotentType = type.toString();
        String content = "";
        if(StringUtils.equals(type.toString(), IdempotentEnum.IdempotentType.PARAM.toString())) {   //參數模式

            if(args.length != 0) {
                StringBuffer sb = new StringBuffer();
                for (Object arg : args) {
                    String argJson = JSON.toJSONString(arg);
                    sb.append(argJson);
                }
                content = Md5Util.encode(sb.toString());
            } else {
                content = "NULL";
            }

        } else if(StringUtils.equals(type.toString(), IdempotentEnum.IdempotentType.UUID.toString())) {  //UUID模式


            if(args.length != 0) {

                Object argument = args[0];
                String str = JSON.toJSONString(argument);

                BaseBean bean = JSON.parseObject(str,BaseBean.class);
                content = bean == null?"":bean.getIdempotentId();
                if(StringUtils.isEmpty(content)) {
                    throw new IdempotentException("UUID类型，UUID必须填写");
                }

            } else {
                content = "NULL";
            }

        }

        //存储redis
        String idmpotentKey = String.format(key,targetName,token,idempotentType,content);
        Boolean exists = jedisUtil.exists(idmpotentKey);
        if(exists) {
            throw new IdempotentException("不能重复操作");
        }

        Long setnx = jedisUtil.setnx(idmpotentKey, DateUtil.getNow(), timeout);
        System.out.println(setnx);

        return pjp.proceed(args);

    }

    @PostConstruct
    public void postConstruct() {
        log.info("Idempotent is pass!");
    }

}
