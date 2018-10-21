package lh.wheel.helper;


import lh.wheel.annotation.mvc.Controller;
import lh.wheel.annotation.mvc.Service;
import lh.wheel.util.ClassUtils;
import lh.wheel.util.ReflectionUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 初始化 bean (Controller、Service)
 */
public class BeanHelper {
    private static final Logger LOGGER = Logger.getLogger(BeanHelper.class);

    private static final Map<Class, Object> controllerMap;
    private static final Map<Class, Object> serviceMap;
    private static final Map<Class, Object> customedBeanMap;

    static {
        Set<Class> classSet = ClassHelper.getClassSet();

        controllerMap = new HashMap<Class, Object>();
        serviceMap = new HashMap<Class, Object>();
        customedBeanMap = new HashMap<Class, Object>();

        loadAnnotatedBeans(classSet);
    }

    /**
     * 加载被注解的 bean (Controller、Service)
     */
    private static void loadAnnotatedBeans(Set<Class> classSet) {
        for(Class clazz:classSet) {
            Controller controller =  (Controller) clazz.getAnnotation(Controller.class);
            Service service =  (Service) clazz.getAnnotation(Service.class);
            if(controller != null || service != null) {
                Object bean = ReflectionUtils.newInstance(clazz);
                if(controller != null)
                    controllerMap.put(clazz, bean);
                if(service != null)
                    serviceMap.put(clazz, bean);
            }
        }
    }

    public static Map<Class, Object> getControllerMap() {
        return controllerMap;
    }

    public static Map<Class, Object> getServiceMap() {
        return serviceMap;
    }

    public static Map<Class, Object> getCustomedBeanMap() {
        return customedBeanMap;
    }

    /**
     * 更新 bean 容器
     * 更新规则：
     * class 若被 Controller 、Service 注解标注，那么加到（更新到）对应的容器中
     * 若都不属于，则加到（更新到）用户自定义bean容器中
     */
    public static void updateBeanMap(Map<Class, Object> newBeanMap) {
        for(Map.Entry<Class, Object> entry:newBeanMap.entrySet()) {
            Class clazz = entry.getKey();
            Object bean = entry.getValue();
            if(clazz.isAnnotationPresent(Controller.class))
                controllerMap.put(clazz, bean);
            else if(clazz.isAnnotationPresent(Service.class))
                serviceMap.put(clazz, bean);
            else
                customedBeanMap.put(clazz, bean);
        }
    }
}
