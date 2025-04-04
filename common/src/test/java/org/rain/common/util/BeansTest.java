package org.rain.common.util;

import lombok.Data;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * created by yangtong on 2025/4/4 下午7:00
 *
 */
public class BeansTest {
    @Test
    public void testCopy() {
        B b = new B();
        System.out.println(b);
        Beans.copyProperties(new A(), b);
        System.out.println(b);
    }

    @Data
    static class A{
        private String a = "AAA";
        private Integer b = 114514;
        private Date c = new Date();
        private String d = "哈哈哈";
    }

    @Data
    static class B{
        private String a;
        private Number b;
        private LocalDateTime c;
        private String e;
    }
}
