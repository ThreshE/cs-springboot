package com.springboot.cs.common.exception;

import com.springboot.cs.common.bean.WebResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonRuntimeException.class)
    @ResponseBody
    public WebResult handleCommonExp(HttpServletRequest request, CommonRuntimeException ex){
        log.error("CommonRuntimeException exception " + ex.getReason());
        WebResult result = new WebResult();
//        result.setMessage(ex.getMessage());
        result.setMessage(ex.getReason());
        result.setResult(false);
        return result;
    }

    @ExceptionHandler(TipRuntimeException.class)
    @ResponseBody
    public WebResult handTipExp(HttpServletRequest request, TipRuntimeException ex){
        log.error("TipRuntimeException exception " + ex.getReason());
        WebResult result = new WebResult();
        result.setMessage(ex.getMessage());
//        result.setMessage(ex.getReason());
        result.setResult(false);
        return result;
    }

    @ExceptionHandler(IdempotentException.class)
    @ResponseBody
    public WebResult handIdeExp(HttpServletRequest request, IdempotentException ex){
        log.error("IdempotentException exception " + ex.getReason());
        WebResult result = new WebResult();
        result.setMessage(ex.getReason());
        result.setResult(false);
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public WebResult handExp(Exception ex){
        log.error("Exception " + ex.getMessage());
        WebResult result = new WebResult();
        result.setMessage("系统异常");
        result.setResult(false);
        return result;
    }
}
