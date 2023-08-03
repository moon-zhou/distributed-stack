package org.moonzhou.onebyone.core;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.moonzhou.onebyone.exception.ApiException;

@Data
@AllArgsConstructor
public class ResponseDTO<T> {

    private Integer code;

    private String msg;

    private T data;

    public static <T> ResponseDTO<T> ok() {
        return build(null, ErrorCode.SUCCESS);
    }

    public static <T> ResponseDTO<T> ok(T data) {
        return build(data, ErrorCode.SUCCESS);
    }

    public static <T> ResponseDTO<T> fail() {
        return build(null, ErrorCode.FAIL);
    }

    public static <T> ResponseDTO<T> fail(T data) {
        return build(data, ErrorCode.FAIL);
    }

    public static <T> ResponseDTO<T> fail(ErrorCodeInterface code) {
        return build(null, code);
    }

    public static <T> ResponseDTO<T> fail(ErrorCodeInterface code, Object... args) {
        return build(null, code, args);
    }

    public static <T> ResponseDTO<T> fail(ApiException exception) {
        return build(exception.getErrorCode().code(), exception.getMessage());
    }

    public static <T> ResponseDTO<T> build(T data, ErrorCodeInterface code, Object... args) {
        return new ResponseDTO<>(code.code(), StrUtil.format(code.message(), args), data);
    }

    public static <T> ResponseDTO<T> build(Integer code, String msg) {
        return new ResponseDTO<>(code, msg, null);
    }

}