package com.springboot.cs.common.exception;

public class BaseRuntimeException extends RuntimeException{

    private String reason;

    private String type;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BaseRuntimeException(String reason, String type) {
        this.reason = reason;
        this.type = type;
    }

    public BaseRuntimeException(String message, String reason, String type) {
        super(message);
        this.reason = reason;
        this.type = type;
    }

    public BaseRuntimeException(String message, Throwable cause, String reason, String type) {
        super(message, cause);
        this.reason = reason;
        this.type = type;
    }

    public BaseRuntimeException(Throwable cause, String reason, String type) {
        super(cause);
        this.reason = reason;
        this.type = type;
    }

    public BaseRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String reason, String type) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.reason = reason;
        this.type = type;
    }
}
