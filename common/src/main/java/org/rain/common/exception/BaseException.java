package org.rain.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rain.common.enums.RespCode;

/**
 * 自定义系统异常类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {
    private Integer code;
    private String message;
    //原始异常类型
    private Throwable throwable;


    public BaseException(String message) {
        this.message = message;
        this.code = RespCode.FAIL.code;
    }

    public BaseException(Throwable cause) {
        super(cause);
        this.throwable = cause;
        this.message = cause.getMessage();
    }

    @Override
    public String toString() {
        return "BaseException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
