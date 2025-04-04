package org.rain.common.convert.support;

import org.rain.common.convert.*;
import org.rain.common.exception.BaseException;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * created by yangtong on 2025/4/4 下午4:59
 * 默认的类型转换器
 */
public class DefaultConversionService implements ConverterRegistry, ConversionService {
    private final Map<TypePair, Converter<?, ?>> converterMap = new ConcurrentHashMap<>();

    public static DefaultConversionService init( String basePackage) {
        return init(basePackage, () -> {
            String path = basePackage.replace('.', '/');
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource(path);
            if (resource == null) {
                throw new BaseException("未找到包路径：" + path);
            }
            return classLoader.getResource(path);
        });
    }

    public static DefaultConversionService init(String basePackage, Supplier<URL> supplier) {
        DefaultConversionService service = new DefaultConversionService();
        URL resource = supplier.get();

        File directory = new File(resource.getFile());
        if (!directory.exists()) {
            throw new BaseException("目录不存在：" + directory.getAbsolutePath());
        }

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            try {
                String className = basePackage + "." + file.getName().replace(".class", "");
                Class<?> clazz = Class.forName(className);

                // 必须是 Converter 的实现类，排除接口本身
                if (Converter.class.isAssignableFrom(clazz) && !clazz.isInterface()) {

                    // 尝试推断泛型类型
                    Type[] genericInterfaces = clazz.getGenericInterfaces();
                    for (Type type : genericInterfaces) {
                        if (type instanceof ParameterizedType pt) {
                            if (pt.getRawType() == Converter.class) {
                                Class<?> sourceType = (Class<?>) pt.getActualTypeArguments()[0];
                                Class<?> targetType = (Class<?>) pt.getActualTypeArguments()[1];
                                // 实例化并注册
                                Converter converter = (Converter) clazz.getDeclaredConstructor().newInstance();
                                service.addConverter(sourceType, targetType, converter);
                                System.out.println("注册转换器：" + clazz.getSimpleName());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new BaseException("加载类失败：" + file.getName());
            }
        }

        return service;
    }

    /**
     * 进行类型转换
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T convert(Object source, Class<T> targetType) {
        //如果为空直接返回
        if (source == null) {
            return null;
        }
        // 如果类型已经匹配，直接返回
        if (targetType.isInstance(source)) {
            return (T) source;
        }

        //使用注册的类型转换器进行转换
        Class<?> sourceType = source.getClass();
        Converter<Object, T> converter = (Converter<Object, T>) converterMap.get(new TypePair(sourceType, targetType));
        if (converter == null) {

            throw new IllegalArgumentException("无法进行类型转换： " + sourceType.getName() + " -> " + targetType.getName());
        }
        return converter.convert(source);
    }

    /**
     * 注册类型转换器
     */
    @Override
    public <S, T> void addConverter(Class<S> sourceType, Class<T> targetType, Converter<S, T> converter) {
        converterMap.put(new TypePair(sourceType, targetType), converter);
        converterMap.put(new TypePair(targetType, sourceType), new ReverseConverter<>(converter));
    }
}
