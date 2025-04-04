package org.rain.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义系统异常类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {
    private Integer code;
    private String message;

    //public BaseException(String message) {
    //    this(Resp.OTHER_ERROR.code, message);
    //}
    //
    //public BaseException(Resp statusCode) {
    //    this(statusCode.code, statusCode.message);
    //}
    //
    //public BaseException(Integer code, String message) {
    //    super(message);
    //    this.code = code;
    //    this.message = message;
    //}
    //
    //public BaseException(String msg, Object... args) {
    //    this(Strs.format(msg, args));
    //}

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message) {
        this.message = message;
        this.code = -1;
    }
}
