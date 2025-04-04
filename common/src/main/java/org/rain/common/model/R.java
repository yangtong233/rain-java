package org.rain.common.model;

import com.google.gson.Gson;
import lombok.Data;
import lombok.experimental.Accessors;
import org.rain.common.enums.RespCode;

/**
 * 统一返回对象，result
 *
 * @param <T> 响应数据类型
 */
@Data
@Accessors(chain = true)
public class R<T> {
    /**
     * 请求是否成功
     */
    protected Boolean success;
    /**
     * 请求状态码
     */
    protected Integer code;
    /**
     * 请求消息
     */
    protected String message;
    /**
     * 请求数据
     */
    protected T data;

    public static R<String> success() {
        return success(null);
    }

    public static R<String> success(String msg) {
        R<String> r = new R<>();
        r.success = RespCode.SUCCESS.status;
        r.code = RespCode.SUCCESS.code;
        r.message = msg;
        r.data = msg;
        return r;
    }

    public static <T> R<T> success(T data) {
        R<T> r = new R<>();
        r.success = RespCode.SUCCESS.status;
        r.code = RespCode.SUCCESS.code;
        r.message = RespCode.SUCCESS.message;
        r.data = data;
        return r;
    }

    public static <T> R<T> success(String msg, T data) {
        R<T> r = new R<>();
        r.success = RespCode.SUCCESS.status;
        r.message = msg;
        r.code = RespCode.SUCCESS.code;
        r.data = data;
        return r;
    }

    public static R<String> error() {
        return error("请求失败");
    }

    public static R<String> error(String msg) {
        R<String> r = new R<>();
        r.success = RespCode.FAIL.status;
        r.code = RespCode.SUCCESS.code;
        r.message = msg;
        return r;
    }

    public static R<String> error(Integer errCode, String msg) {
        R<String> r = new R<>();
        r.success = false;
        r.code = errCode;
        r.message = msg;
        return r;
    }

    public static R<String> error(RespCode respCode) {
        R<String> r = new R<>();
        r.success = respCode.status;
        r.code = respCode.code;
        r.message = respCode.message;
        return r;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
