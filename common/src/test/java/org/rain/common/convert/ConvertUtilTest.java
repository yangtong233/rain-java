package org.rain.common.convert;

import org.junit.Test;
import org.rain.common.convert.support.DefaultConversionService;

/**
 * created by yangtong on 2025/4/4 下午5:38
 */
public class ConvertUtilTest {
    @Test
    public void test() {
        String basePackage = "org.rain.common.convert.converters";
        DefaultConversionService init = DefaultConversionService.init(basePackage);
        System.out.println(init.convert("1223123", String.class));
    }

    @Test
    public void testUtil() {
        System.out.println(Converts.convert(123213, Number.class));
    }
}
