package org.moonzhou.distributedstack.springboot4performancetest.dto;

/**
 * @author moon zhou
 */
public class Result<T> {
    private int code;
    private String message;
    private T data;

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result<Void> success() {
        return new Result<Void>(200, null, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(200, (String) null, data);
    }

    public static Result<Void> failed(int code, String message) {
        return new Result<Void>(code, message, null);
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public Result() {
    }
}