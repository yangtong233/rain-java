package org.rain.common.enums;

/**
 * created by yangtong on 2025/4/4 下午7:11
 * <br/>
 * 自定义的响应状态码
 */
public enum RespCode {
    // ==================== -1 请求错误，未知错误 ====================
    FAIL(-1, false, "请求错误")
    // ==================== 0 成功 ====================
    ,SUCCESS(0, true, "请求成功")

    // ==================== 1-99 登录相关 ====================
    ,NOT_LONGIN(1, false, "未登录，请先登录")
    ,LONGIN_TIME_OUT(2, false, "登录过期，请重新登录")
    ,UNKNOWN_USER(3, false, "用户不存在")
    ,PASSWORD_ERROR(4, false, "密码错误")
    ,UNKNOWN_USER_PASSWORD(5, false, "账号或密码错误")
    ,BANNED_USER(6, false, "被禁止的用户")
    ,CAPTCHA_ERROR(7, false, "验证码错误")
    ,LONGIN_FREQUENTLY(8, false, "登录太频繁")

    // ==================== 101-199 参数或数据格式相关 ====================
    ,PARAM_ERROR(101, false, "参数错误")
    ,MISSING_PARAM(102, false, "缺少必要参数")
    ,FORMAT_ERROR(103, false, "数据格式不正确")
    ,VALIDATION_FAILED(104, false, "数据校验失败")

    // ==================== 200-299 权限相关 ====================
    ,NO_PERMISSION(201, false, "没有权限")
    ,ACCESS_DENIED(202, false, "访问被拒绝")
    ,ROLE_NOT_ALLOWED(203, false, "角色不允许访问该资源")

    // ==================== 300-399 系统错误 ====================
    ,SERVER_ERROR(301, false, "服务器内部错误")
    ,SERVICE_UNAVAILABLE(302, false, "服务不可用")
    ,TIMEOUT(303, false, "请求超时")


    // ==================== 400-499 资源相关 ====================
    ,NOT_FOUND(401, false, "资源不存在")
    ,DUPLICATE_RESOURCE(402, false, "资源已存在")
    ,RESOURCE_LOCKED(403, false, "资源被锁定")
    ,UNKNOWN_ERROR(404, false, "资源未找到")

    // ==================== 500-599 第三方服务 ====================
    ,THIRD_PARTY_ERROR(501, false, "第三方服务异常")
    ,PC_ERROR(502, false, "远程服务调用失败")
    ,THIRD_PARTY_TIMEOUT(503, false, "第三方接口超时")
    ;

    /**
     * 请求结果状态码
     */
    public final Integer code;
    /**
     * 请求是否成功
     */
    public final Boolean status;
    /**
     * 请求结果状态说明
     */
    public final String message;

    RespCode(Integer code, Boolean status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return "RespCode{" +
                "code=" + code +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
