package org.rain.common.util.strings;

import org.rain.common.convert.Converts;
import org.rain.common.enums.Placeholder;

import java.util.Map;
import java.util.Random;

/**
 * created by yangtong on 2025/4/4 下午3:40
 * 字符串工具类
 */
public class Strs {
    private static final String resourceStr = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM`~!@#$%^&*()_+[];',./【】；‘，。、{}:\"<>?";
    private static final Random random = new Random();

    /**
     * 得到指定长度的随机字符串<br/>
     *
     * @param n     指定长度
     * @param bound 种子字符串边界
     * @return 随机字符串
     */
    private static String randomStr(int n, int bound) {
        char[] str = new char[n];
        for (int i = 0; i < n; i++) {
            //生成一个大于等于0且小于bound的随机整数。
            int index = random.nextInt(bound);
            //根据随机整数获取字符串
            str[i] = resourceStr.charAt(index);
        }
        return new String(str);
    }

    /**
     * 得到指定的长度的随机数字<br/>
     *
     * @param n 长度
     * @return 随机数字
     */
    public static String randomNumber(int n) {
        return randomStr(n, 10);
    }

    /**
     * 得到指定的长度的随机字符串<br/>
     *
     * @param n 长度
     * @return 随机字符串
     */
    public static String randomStr(int n) {
        return randomStr(n, resourceStr.length());
    }

    /**
     * 将目标数字变成指定位数的字符串，不足前面填充0<br/>
     *  fillZero(123, 5) -> "00123"
     * @param targetNum 目标数字
     * @param length    字符串长度
     * @return 指定长度的字符串
     */
    public static String fillZero(int targetNum, int length) {
        String targetNumStr = String.valueOf(targetNum);
        if (targetNumStr.length() >= length) return targetNumStr;
        return "0".repeat(length - targetNumStr.length()) + targetNumStr;
    }

    /**
     * 判断target是否为空白字符串
     *
     * @param target 目标字符串
     * @return 是否为空白字符串
     */
    public static boolean isEmpty(String target) {
        return target == null || target.trim().isBlank();
    }

    /**
     * 判断target是否不为空白字符串
     *
     * @param target 目标字符串
     * @return 是否不为空白字符串
     */
    public static boolean isNotEmpty(String target) {
        return !isEmpty(target);
    }

    /**
     * 判断是否为数字
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        return str.matches("\\d+");
    }

    /**
     * 判断字符是否是汉字
     */
    public static boolean isChinese(char ch) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_E
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT;
    }

    /**
     * 判断字符串是否全为汉字
     */
    public static boolean isChinese(String str) {
        if (str == null || str.isEmpty()) return false;
        for (char c : str.toCharArray()) {
            if (!isChinese(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据前缀、后缀、数据源解析占模板字符串的占位符并填充数据到对应占位符<br/>
     *
     * @param templateStr 模板字符串
     * @param datasource  数据源
     * @param prefix      占位符前缀
     * @param suffix      占位符后缀
     * @return 解析占位符并填充数据后的字符串
     */
    public static String parseByPlaceholder(String templateStr, Map<String, Object> datasource, Placeholder prefix, Placeholder suffix) {
        if (isEmpty(templateStr)) {
            return "";
        }
        //用来保存格式化之后的字符串
        StringBuilder resultStr = new StringBuilder();

        //设置快慢指针，low遇到prefix停下来，high遇到suffix时如果low处于停止状态则high也停下来
        int low = 0, high = 0;
        //记录low、high指针的停止与否，false表示没有停止
        boolean lowStatus = false, highStatus = false;
        //缓存lowStatus = true时，遍历到的字符串
        StringBuilder cache = new StringBuilder();
        for (int i = 0; i < templateStr.length(); i++) {
            char currentChar = templateStr.charAt(i);

            //前缀成功匹配，停下慢指针
            if (templateStr.startsWith(prefix.placeholder, i)) {
                lowStatus = true;
                //如果遇到新的前缀时，上一个前缀还没有闭合，就将缓存写入sb，并清空缓存
                if (!cache.isEmpty()) {
                    resultStr.append(cache);
                    cache = new StringBuilder();
                    low = i;
                }
            }
            //前缀后缀都匹配成功，停下快指针
            if (lowStatus && templateStr.startsWith(suffix.placeholder, i)) {
                highStatus = true;
            }

            //如果快慢指针都是停下的，说明占位符前后缀成功闭合了，根据占位符从数据源获取数据追加到sb，并清空缓存
            if (lowStatus && highStatus) {
                String key = templateStr.substring(low + prefix.placeholder.length(), high);
                String value = Converts.convert(datasource.getOrDefault(key, ""), String.class);
                resultStr.append(value);
                //reset
                lowStatus = false;
                highStatus = false;
                low = high = i + suffix.placeholder.length();
                //"${"成功闭合后
                cache = new StringBuilder();
                continue;
            }

            //low指针没有停下来
            if (!lowStatus) {
                low++;
                resultStr.append(currentChar);
            }
            //low指针停下里了，就缓存当前遍历的字符串
            else {
                cache.append(currentChar);
            }
            //为了防止缓存还没有写入resultStr循环就结束了的情况，在最后一次循环时将缓存数据写入sb
            if (i == templateStr.length() - 1) {
                resultStr.append(cache);
            }

            high++;
        }

        return resultStr.toString();
    }

    /**
     * 解析并格式化模板字符串中的"${xxx}" <br/>
     * eg: 数据源{name:"zs", age:23} <br/>
     * "hello ${name}!!!${age}" ---> "hello zs!!!23" <br/>
     * "hello ${name!!!${age}" ---> "hello ${name!!!23" <br/>
     * "hello ${name!!!${age${$" ---> "hello zs!!!${age${$" <br/>
     *
     * @param templateStr 模板字符串
     * @param datasource  数据源
     * @return 格式化之后的字符串
     */
    public static String format(String templateStr, Map<String, Object> datasource) {
        return parseByPlaceholder(
                templateStr, datasource,
                Placeholder.LEFT_CURLY_BRACE_DOLLAR,
                Placeholder.RIGHT_CURLY_BRACE
        );
    }

    /**
     * 格式化模板字符串，将不定参数填充到目标字符串的对应位置上 <br/>
     * eg: <br/>
     * format("my name is {}, and age is {}", "zs", 23) -> "my name is zs, and age is 23" <br/>
     * format("my name is {}, and age is {}", "zs") -> "my name is zs, and age is " <br/>
     * format("my name is {}", "狗剩", 22) -> "my name is 狗剩" <br/>
     *
     * @param templateStr 模板字符串
     * @param args        参数
     * @return 格式化之后的字符串
     */
    public static String format(String templateStr, Object... args) {
        //用来保存格式化之后的字符串
        StringBuilder resultStr = new StringBuilder();

        //指向args数组的指针
        int index = 0;
        for (int i = 0; i < templateStr.length(); i++) {
            char currentChar = templateStr.charAt(i);
            char nextChar = templateStr.charAt(Integer.min(i + 1, templateStr.length() - 1));
            if (currentChar == '{' && nextChar == '}') {
                i++;
                if (index < args.length) {
                    resultStr.append(args[index++]);
                }
                continue;
            }
            resultStr.append(templateStr.charAt(i));
        }

        return resultStr.toString();
    }

}
