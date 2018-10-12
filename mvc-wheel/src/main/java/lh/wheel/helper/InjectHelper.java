package lh.wheel.helper;

import lh.wheel.annotation.Inject;
import lh.wheel.util.ReflectionUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 解析 @Inject，实现依赖注入
 */
public class InjectHelper {
    private static final Logger LOGGER = Logger.getLogger(InjectHelper.class);

    static {
        Map<Class, Object> controllerMap = BeanHelper.getControllerMap();
        Map<Class, Object> serviceMap = BeanHelper.getServiceMap();

        inject(controllerMap, serviceMap);
    }

    /**
     * 注入实现函数
     */
    private static void inject(Map<Class, Object> controllerMap, Map<Class, Object> serviceMap) {
        for(Class clazz:controllerMap.keySet()) {
            Field[] fields = clazz.getDeclaredFields();
            for(Field field:fields) {
                Inject inject = field.getAnnotation(Inject.class);
                if(inject != null) {
                    Object service = serviceMap.get(field.getType());
                    if(service == null) {
                        String errorMess = field.getDeclaringClass().getSimpleName()
                                +" 类中 "+field.getName()+" 字段注入失败，不存在该类型的bean";
                        LOGGER.error(errorMess);
                        throw new RuntimeException("自动注入失败："+errorMess);
                    }

                    ReflectionUtils.setField(controllerMap.get(clazz), field, service);
                }
            }
        }
    }
}
