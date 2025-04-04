package org.rain.common.convert;

import org.rain.common.convert.support.DefaultConversionService;

/**
 * created by yangtong on 2025/4/4 下午5:58
 * <类型转换工具类/>
 */
public class Converts {

    private static final DefaultConversionService conversionService;

    static {
        String basePackage = "org.rain.common.convert.converters";
        conversionService = DefaultConversionService.init(basePackage);
    }

    public static DefaultConversionService get() {
        return conversionService;
    }

    public static <T> T convert(Object source, Class<T> targetType) {
        return conversionService.convert(source, targetType);
    }

}
