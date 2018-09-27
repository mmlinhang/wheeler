package lh.wheeler.util;

import org.apache.log4j.Logger;

/**
 * 类型转换工具类
 * 封装了下列功能：
 * 1. 转型出错处理
 * 2. 若转型异常或 obj 为空时，提供默认值
 */
public final class CastUtils {

    private final static Logger LOGGER = Logger.getLogger(CastUtils.class);

    /**
     * 转型为 String 类型， 默认值为  ""
     */
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /**
     * 转型为 String 类型， 可设定默认值
     */
    public static String castString(Object obj, String defaultValue) {
        return obj == null ? defaultValue : String.valueOf(obj);
    }

    /**
     * 转型为 int 类型， 默认值为 0
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     * 转型为 int 类型， 可设定默认值
     */
    public static int castInt(Object obj, int defaultValue) {
        int value = defaultValue;
        if(obj != null) {
            try {
                value = Integer.parseInt(castString(obj));
            }
            catch(NumberFormatException e) {
                LOGGER.error(obj+" 解析为 int 失败");
                value = defaultValue;
            }
        }

        return value;
    }

    /**
     * 转型为 double 类型， 默认值为 0.0
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0.0);
    }

    /**
     * 转型为 double 类型， 可设定默认值
     */
    public static double castDouble(Object obj, double defaultValue) {
        double value = defaultValue;
        if(obj != null) {
            try {
                value = Double.parseDouble(castString(obj));
            }
            catch(NumberFormatException e) {
                LOGGER.error(obj+" 解析为 double 失败");
                value = defaultValue;
            }
        }

        return value;
    }

    /**
     * 转型为 long 类型， 默认值为 0
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0L);
    }

    /**
     * 转型为 long 类型， 可设定默认值
     */
    public static long castLong(Object obj, long defaultValue) {
        long value = defaultValue;
        if(obj != null) {
            try {
                value = Long.parseLong(castString(obj));
            }
            catch(NumberFormatException e) {
                LOGGER.error(obj+" 解析为 long 失败");
                value = defaultValue;
            }
        }

        return value;
    }

    /**
     * 转型为 boolean 类型， 默认值为 false
     */
    public static Boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     * 转型为 boolean 类型， 可设定默认值
     */
    public static Boolean castBoolean(Object obj, boolean defaultValue) {

        return obj == null ? defaultValue : Boolean.getBoolean(castString(defaultValue));
    }
}
