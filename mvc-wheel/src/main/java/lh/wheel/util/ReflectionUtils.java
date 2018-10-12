package lh.wheel.util;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;

/**
 * 反射工具类
 */
public class ReflectionUtils {
    private static final Logger LOGGER = Logger.getLogger(ReflectionUtils.class);

    /**
     * 利用反射机制实例化 clazz 对象
     */
    public static Object newInstance(Class clazz) {
        try{
            return clazz.newInstance();
        }
        catch(Exception e) {
            LOGGER.error("实例化 "+clazz+" 错误");
            throw new RuntimeException(e);
        }
    }

    /**
     * 利用反射机制设置对象的某一字段
     */
    public static void setField(Object object, Field field, Object value) {
        field.setAccessible(true);
        try{
            field.set(object, value);
        }
        catch(Exception e) {
            LOGGER.error(object+" 设置字段 "+field.getName()+" 失败");
            throw new RuntimeException(e);
        }
    }
}
