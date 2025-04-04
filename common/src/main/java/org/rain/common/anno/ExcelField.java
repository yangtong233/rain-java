package org.rain.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * created by yangtong on 2025/4/4 下午4:46
 * <br/>Excel字段
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value= {ElementType.FIELD})
public @interface ExcelField {
    String value() default "";

    String name() default "";

    String url() default "";

    String dictCode() default "";

    int width() default 15;
}