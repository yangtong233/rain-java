package org.rain.common.holder;

import org.rain.common.model.LoginUser;

/**
 * created by yangtong on 2025/4/4 下午8:41
 * <br/>
 * 当前登录用户上下文
 */
public class U {
    private static final ThreadLocal<LoginUser> CURRENT_USER = new ThreadLocal<>();

    public static void set(LoginUser user) {
        CURRENT_USER.set(user);
    }

    public static LoginUser get() {
        return CURRENT_USER.get();
    }

    public static void clear() {
        CURRENT_USER.remove();
    }

}
