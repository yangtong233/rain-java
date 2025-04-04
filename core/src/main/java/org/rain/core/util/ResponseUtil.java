package org.rain.core.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * created by yangtong on 2025/4/4 下午7:09
 *
 * @Description:
 */
public class ResponseUtil {
    /**
     * 给响应添加支持跨域
     */
    public static void setCors() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();

        //是否允许在跨域请求中发送身份验证凭据
        setHeaderIfPresent(response, "Access-Control-Allow-Credentials", "true");
        //设置允许跨域访问的域名，这里设置为允许所有域名(Access-Control-Allow-Credentials为true，那么Access-Control-Allow-Origin不能为*)
        String remoteIp = request.getHeader("Origin");
        setHeaderIfPresent(response, "Access-Control-Allow-Origin", remoteIp);
        //设置允许跨域访问的请求方法
        setHeaderIfPresent(response, "Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        //设置允许跨域访问的自定义头
        setHeaderIfPresent(response, "Access-Control-Allow-Headers", "Content-Type, Authorization, *");
        setHeaderIfPresent(response, "Access-Control-Expose-Headers", "*");
    }

    /**
     * 添加响应头
     */
    public static void setHeaderIfPresent(HttpServletResponse response, String key, String value) {
        if (response.getHeader(key) == null) {
            response.setHeader(key, value);
        }
    }
}
