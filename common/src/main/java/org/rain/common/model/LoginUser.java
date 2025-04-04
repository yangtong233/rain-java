package org.rain.common.model;

import lombok.Data;

/**
 * created by yangtong on 2025/4/4 下午8:43
 * <br/>
 * 当前登录用户
 */
@Data
public class LoginUser {
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户真是姓名
     */
    private String realName;

    /**
     * 用户当前部门
     */
    private String departCode;

}
