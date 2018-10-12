package lh.wheel.helper;


import lh.wheel.annotation.Controller;
import lh.wheel.annotation.Service;
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

    static {
        String basePackage = ConfigHelper.getBasePackage();
        Set<Class> classSet = ClassUtils.getClassSet(basePackage.replace(".", "/"));

        controllerMap = new HashMap<Class, Object>();
        serviceMap = new HashMap<Class, Object>();

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
}
