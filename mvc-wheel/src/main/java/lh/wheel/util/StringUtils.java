package lh.wheel.util;

import org.apache.log4j.Logger;

/**
 * 字符串工具
 */
public final class StringUtils {

    private static final Logger LOGGER = Logger.getLogger(StringUtils.class);

    /**
     * 判断字符串是否为空
     * 为空条件：
     * 1. str 为 null
     * 2. str 为 空串
     * 3. str 为 "     "  (1或多个空格)
     */
    public static boolean isEmpty(String str) {
        if(str != null)
            str = str.trim();

        return org.apache.commons.lang3.StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
