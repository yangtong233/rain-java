package org.rain.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动记录日志
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.TYPE,             // 类、接口
        ElementType.FIELD,           // 字段
        ElementType.METHOD,          // 方法
        ElementType.PARAMETER,       // 方法参数
        ElementType.CONSTRUCTOR,     // 构造方法
        ElementType.LOCAL_VARIABLE,  // 局部变量
        ElementType.ANNOTATION_TYPE, // 注解类型
        ElementType.PACKAGE,         // 包
        ElementType.TYPE_PARAMETER,  // 泛型参数（Java 8+）
        ElementType.TYPE_USE         // 任意类型使用位置（Java 8+）
})
public @interface AutoLog {
    String value() default "";
}
