package org.rain.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * created by yangtong on 2025/4/4 下午6:40
 * <br/>
 * Bean 操作工具类
 */
public class Beans {

    /**
     * 将source对象的字段复制到target字段
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target, String... ignoreFieldNames) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("源对象或目标对象不能为空");
        }
        Set<String> ignoreSet = Arrays.stream(ignoreFieldNames).collect(Collectors.toSet());
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        //获取源对象的所有字段
        Field[] sourceFields = sourceClass.getDeclaredFields();
        for (Field sourceField : sourceFields) {
            String fieldName = sourceField.getName();
            if (ignoreSet.contains(fieldName)) {
                continue;
            }
            //获取该字段的get方法和set方法
            String capitalized = Strs.firstCapitalize(fieldName);
            String getterName = "get" + capitalized;
            String setterName = "set" + capitalized;

            try {
                //判断该字段是否有getter方法
                Method getterMethod = null;
                try {
                    getterMethod = sourceClass.getMethod(getterName);
                } catch (NoSuchMethodException e) {
                    // 如果该字段是 boolean 类型可能是 isXxx
                    if (sourceField.getType() == boolean.class || sourceField.getType() == Boolean.class) {
                        try {
                            getterMethod = sourceClass.getMethod("is" + capitalized);
                        } catch (NoSuchMethodException ignored) {
                        }
                    }
                }
                if (getterMethod == null) {
                    continue; // 没有 getter 方法，跳过
                }

                //走到这，说明该源字段肯定有getter方法
                Object value = getterMethod.invoke(source);
                // 不复制 null 值
                if (value == null) continue;

                //查找目标对象里面的同名字段
                Field targetField;
                try {
                    targetField = targetClass.getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                    continue; // 目标中没有对应字段，跳过
                }

                //检查类型兼容性，源对象的字段是否能否赋值给目标字段
                if (!targetField.getType().isAssignableFrom(value.getClass())) {
                    continue; // 不兼容就不赋值
                }

                //如果兼容，则调用目标字段的setter方法调用
                Method setterMethod;
                try {
                    setterMethod = targetClass.getMethod(setterName, targetField.getType());
                } catch (NoSuchMethodException e) {
                    continue; // 没有 setter 方法，跳过
                }
                setterMethod.invoke(target, value);

            } catch (Exception e) {
                //TODO 打印日志或记录异常
                System.err.println("属性拷贝出错：" + e.getMessage());
            }
        }
    }

}
