package org.rain.common.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by yangtong on 2025/4/4 下午10:51
 *
 * @Description:
 */
public class ExceptionUtil {
    public ExceptionUtil() {
    }

    public static String getMessage(Throwable e) {
        return null == e ? "null" : Strs.format("{}: {}", e.getClass().getSimpleName(), e.getMessage());
    }

    public static String getSimpleMessage(Throwable e) {
        return null == e ? "null" : e.getMessage();
    }

    public static RuntimeException wrapRuntime(Throwable throwable) {
        return throwable instanceof RuntimeException ? (RuntimeException)throwable : new RuntimeException(throwable);
    }

    public static RuntimeException wrapRuntime(String message) {
        return new RuntimeException(message);
    }

    //public static <T extends Throwable> T wrap(Throwable throwable, Class<T> wrapThrowable) {
    //    return wrapThrowable.isInstance(throwable) ? throwable : (Throwable)ReflectUtil.newInstance(wrapThrowable, new Object[]{throwable});
    //}

    public static void wrapAndThrow(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
        } else if (throwable instanceof Error) {
            throw (Error)throwable;
        } else {
            throw new UndeclaredThrowableException(throwable);
        }
    }

    public static void wrapRuntimeAndThrow(String message) {
        throw new RuntimeException(message);
    }

    public static Throwable unwrap(Throwable wrapped) {
        Throwable unwrapped = wrapped;

        while(true) {
            while(!(unwrapped instanceof InvocationTargetException)) {
                if (!(unwrapped instanceof UndeclaredThrowableException)) {
                    return unwrapped;
                }

                unwrapped = ((UndeclaredThrowableException)unwrapped).getUndeclaredThrowable();
            }

            unwrapped = ((InvocationTargetException)unwrapped).getTargetException();
        }
    }

    public static StackTraceElement[] getStackElements() {
        return Thread.currentThread().getStackTrace();
    }

    public static StackTraceElement getStackElement(int i) {
        return Thread.currentThread().getStackTrace()[i];
    }

    //public static StackTraceElement getStackElement(String fqcn, int i) {
    //    StackTraceElement[] stackTraceArray = Thread.currentThread().getStackTrace();
    //    int index = ArrayUtil.matchIndex((ele) -> {
    //        return StrUtil.equals(fqcn, ele.getClassName());
    //    }, stackTraceArray);
    //    return index > 0 ? stackTraceArray[index + i] : null;
    //}

    public static StackTraceElement getRootStackElement() {
        StackTraceElement[] stackElements = Thread.currentThread().getStackTrace();
        return Thread.currentThread().getStackTrace()[stackElements.length - 1];
    }

    public static String stacktraceToOneLineString(Throwable throwable) {
        return stacktraceToOneLineString(throwable, 3000);
    }

    public static String stacktraceToOneLineString(Throwable throwable, int limit) {
        Map<Character, String> replaceCharToStrMap = new HashMap();
        replaceCharToStrMap.put('\r', " ");
        replaceCharToStrMap.put('\n', " ");
        replaceCharToStrMap.put('\t', " ");
        return stacktraceToString(throwable, limit, replaceCharToStrMap);
    }

    public static String stacktraceToString(Throwable throwable) {
        return stacktraceToString(throwable, 3000);
    }

    public static String stacktraceToString(Throwable throwable, int limit) {
        return stacktraceToString(throwable, limit, (Map)null);
    }

    public static String stacktraceToString(Throwable throwable, int limit, Map<Character, String> replaceCharToStrMap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        throwable.printStackTrace(new PrintStream(baos));
        String exceptionStr = baos.toString();
        int length = exceptionStr.length();
        if (limit < 0 || limit > length) {
            limit = length;
        }

        if (replaceCharToStrMap != null && !replaceCharToStrMap.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < limit; ++i) {
                char c = exceptionStr.charAt(i);
                String value = (String)replaceCharToStrMap.get(c);
                if (null != value) {
                    sb.append(value);
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        } else {
            return limit == length ? exceptionStr : Strs.subPre(exceptionStr, limit);
        }
    }

    public static boolean isCausedBy(Throwable throwable, Class<? extends Exception>... causeClasses) {
        return null != getCausedBy(throwable, causeClasses);
    }

    public static Throwable getCausedBy(Throwable throwable, Class<? extends Exception>... causeClasses) {
        for(Throwable cause = throwable; cause != null; cause = cause.getCause()) {
            Class[] var3 = causeClasses;
            int var4 = causeClasses.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Class<? extends Exception> causeClass = var3[var5];
                if (causeClass.isInstance(cause)) {
                    return cause;
                }
            }
        }

        return null;
    }



    public static List<Throwable> getThrowableList(Throwable throwable) {
        ArrayList list;
        for(list = new ArrayList(); throwable != null && !list.contains(throwable); throwable = throwable.getCause()) {
            list.add(throwable);
        }

        return list;
    }

    public static Throwable getRootCause(Throwable throwable) {
        List<Throwable> list = getThrowableList(throwable);
        return list.size() < 1 ? null : (Throwable)list.get(list.size() - 1);
    }

    public static String getRootCauseMessage(Throwable th) {
        return getMessage(getRootCause(th));
    }
}
