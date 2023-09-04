package org.moonzhou.transaction.common.exception;

import java.io.Serializable;

public class NotFoundException extends RuntimeException {
    private String biz;
    private Serializable id;

    public NotFoundException(String biz, Serializable id) {
        this.biz = biz;
        this.id = id;
    }

    public NotFoundException(String message, String biz, Serializable id) {
        super(message);
        this.biz = biz;
        this.id = id;
    }

    public NotFoundException(String message, Throwable cause, String biz, Serializable id) {
        super(message, cause);
        this.biz = biz;
        this.id = id;
    }

    public NotFoundException(Throwable cause, String biz, Serializable id) {
        super(cause);
        this.biz = biz;
        this.id = id;
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String biz, Serializable id) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.biz = biz;
        this.id = id;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public Serializable getId() {
        return id;
    }

    public void setId(Serializable id) {
        this.id = id;
    }
}