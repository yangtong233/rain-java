package org.rain.core.auth.handler.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.rain.common.enums.DateUnit;
import org.rain.common.enums.RespCode;
import org.rain.common.exception.BaseException;
import org.rain.common.holder.U;
import org.rain.common.model.LoginUser;
import org.rain.common.util.C;
import org.rain.core.auth.handler.AuthenticateHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 认证默认实现
 */
public class DefaultAuthenticateHandler implements AuthenticateHandler, ApplicationContextAware {

    private Long timeOut = -1L;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        timeOut = context.getEnvironment().getProperty("system.auth.time-out", Long.class);
    }

    @Override
    public boolean handler(HttpServletRequest request, HttpServletResponse response) {
        //1.先看请求头中有没有一个叫Authorization的请求头
        String authorization = request.getHeader("Authorization");
        //2.没有的话再看有没有cookie
        if (authorization == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                cookies = new Cookie[0];
            }
            Map<String, String> map = Arrays.stream(cookies).collect(Collectors.toMap(Cookie::getName, Cookie::getValue));
            //cookie中没有id，则认为当前未登录
            if (!map.containsKey("Authorization")) {
                throw new BaseException(RespCode.NOT_LONGIN);
            }
            authorization = map.get("Authorization");
        }

        //3.根据令牌查看缓存有没有该数据
        LoginUser loginUser = C.get("onlineUser::" + authorization);
        //4.缓存中没有当前令牌信息，说明登录过期，用户信息已经从缓存中被清除了
        if (loginUser == null) {
            throw new BaseException(RespCode.LONGIN_TIME_OUT);
        }
        //5.走到这，说明当前用户已经登录了，认证通过，更新当前用户的访问信息
        loginUser.setLastRequestTime(System.currentTimeMillis());
        loginUser.setRequestCount(loginUser.getRequestCount() + 1);
        C.put("onlineUser::" + authorization, loginUser, DateUnit.SECOND, timeOut);

        //6.设置当前用户上下文
        U.set(loginUser);
        return true;
    }
}
