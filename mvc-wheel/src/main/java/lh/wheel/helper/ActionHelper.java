package lh.wheel.helper;

import lh.wheel.annotation.Action;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析 @Action
 */
public class ActionHelper {
    private static final Logger LOGGER = Logger.getLogger(ActionHelper.class);

    private static final Map<Handler, Method> serviceMethodMap;

    static {
        serviceMethodMap = new HashMap<Handler, Method>();
        Map<Class, Object> controllerMap = BeanHelper.getControllerMap();
        parseAction(controllerMap);
    }

    /**
     * 解析 @Action
     */
    private static void parseAction(Map<Class, Object> controllerMap) {
        for(Class clazz:controllerMap.keySet()) {
            Method[] methods = clazz.getDeclaredMethods();
            for(Method method:methods) {
                Action action = method.getAnnotation(Action.class);
                if(action != null) {
                    //判断该 action 注解的方法是否可访问
                    if(!Modifier.isPublic(method.getModifiers())) {
                        String errorMess = clazz.getSimpleName()+": "+method.getName()+" 方法不可访问：必须为 public";
                        LOGGER.error(errorMess);
                        throw new RuntimeException(errorMess);
                    }

                    String actionMethod = action.method();
                    String actionUrl = action.url();
                    Handler handler = new Handler(actionMethod, actionUrl);
                    serviceMethodMap.put(handler, method);
                }
            }
        }
    }

    public static Method getMappedServiceMethod(String method, String url) {
        Handler handler = new Handler(method, url);
        return serviceMethodMap.get(handler);
    }
}
