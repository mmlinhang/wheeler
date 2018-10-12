package lh.wheel.util;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 解析 .properties 配置文件
 */
public final class PropsUtils {

    private static final Logger LOGGER = Logger.getLogger(PropsUtils.class);

    /**
     * 将配置文件转换为 Properties 对象
     * @return 若解析错误返回 null
     */
    public static Properties loadProps(String fileName) {
        InputStream is = null;
        Properties props;

        try{
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(is == null)
                throw new FileNotFoundException(fileName+"未找到");

            props = new Properties();
            props.load(is);
        }
        //此处可能捕获两个地方产生的异常：
        //1. 上面显示抛出的 FileNotFoundException 异常
        //2. 调用 props.load() 抛出的 IOException 异常
        catch(IOException e) {
            LOGGER.error(fileName+" 文件加载为 properties 对象失败", e);
            props = null;
        }
        finally {
            try {
                if(is != null)
                    is.close();
            }
            catch(IOException e) {
                LOGGER.error(fileName+" 文件关闭失败");
            }
        }

        return props;
    }

    /**
     * 根据 key 获得 String 类型的值
     * @return 若 props 不包含该 key，打印错误信息并返回 null
     */
    public static String getString(Properties props, String key) {
        if(!props.containsKey(key))
            LOGGER.error(props+" 不包含该 "+key+" key");

        return getString(props, key, null);
    }

    /**
     * 根据 key 获得 String 类型的值
     * @return 若 props 不包含该 key， 返回指定的 defaultValue
     */
    public static String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if(props.containsKey(key))
            value = props.getProperty(key);

        return value;
    }

    /**
     * 根据 key 获得 int 类型的值
     * @return 若 props 不包含该 key，打印错误信息并返回 0
     */
    public static int getInt(Properties props, String key) {
        if(!props.containsKey(key))
            LOGGER.error(props+" 不包含该 "+key+" key");

        return getInt(props, key, 0);
    }

    /**
     * 根据 key 获得 int 类型的值
     * @return 若 props 不包含该 key 或 解析为 int 类型出错， 返回指定的 defaultValue
     */
    public static int getInt(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if(props.containsKey(key))
            value = CastUtils.castInt(props.getProperty(key), defaultValue);

        return value;
    }

    /**
     * 根据 key 获得 double 类型的值
     * @return 若 props 不包含该 key，打印错误信息并返回 0.0
     */
    public static double getDouble(Properties props, String key) {
        if(!props.containsKey(key))
            LOGGER.error(props+" 不包含该 "+key+" key");

        return getDouble(props, key, 0.0);
    }

    /**
     * 根据 key 获得 double 类型的值
     * @return 若 props 不包含该 key 或 解析为 double 类型出错， 返回指定的 defaultValue
     */
    public static double getDouble(Properties props, String key, double defaultValue) {
        double value = defaultValue;
        if(props.containsKey(key))
            value = CastUtils.castDouble(props.getProperty(key), defaultValue);

        return value;
    }

    /**
     * 根据 key 获得 boolean 类型的值
     * @return 若 props 不包含该 key，打印错误信息并返回 false
     */
    public static boolean getBoolean(Properties props, String key) {
        if(!props.containsKey(key))
            LOGGER.error(props+" 不包含该 "+key+" key");

        return getBoolean(props, key, false);
    }

    /**
     * 根据 key 获得 boolean 类型的值
     * @return 若 props 不包含该 key 或 解析为 boolean 类型出错， 返回指定的 defaultValue
     */
    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if(props.containsKey(key))
            value = CastUtils.castBoolean(props.getProperty(key), defaultValue);

        return value;
    }
}