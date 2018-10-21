package lh.wheel.helper;

import lh.wheel.annotation.aop.Aspect;
import lh.wheel.aop.advice.Advice;
import lh.wheel.aop.proxy.ProxyManager;
import lh.wheel.util.ClassUtils;
import lh.wheel.util.ReflectionUtils;

import java.util.*;

public class AopHelper {

    static {
        Set<Class> classSet = ClassHelper.getClassSet();
        Map<Advice, Set<Class>> targetClassMap = getTargetClassMap(classSet);
        Map<Class, List<Advice>> adviceMap = convert2AdviceMap(targetClassMap);

        Map<Class, Object> proxyMap = getProxyMap(adviceMap);
        BeanHelper.updateBeanMap(proxyMap);
    }

    /**
     * 获取被用户注解的增强对象和被代理目标类的映射
     */
    private static Map<Advice, Set<Class>> getTargetClassMap(Set<Class> classSet) {
        Map<Advice, Set<Class>> targetClassMap = new HashMap<Advice, Set<Class>>();

        Set<Class> adviceClassSet = ClassUtils.getClassSetBySuperClass(classSet, Advice.class);
        for(Class clazz:adviceClassSet) {
            if(clazz.isAnnotationPresent(Aspect.class)) {
                Class targetAnnotation = ((Aspect) clazz.getAnnotation(Aspect.class)).value();
                Set<Class> targetClassSet = ClassUtils.getClassSetWithAnnotation(classSet, targetAnnotation);
                targetClassMap.put((Advice) ReflectionUtils.newInstance(clazz), targetClassSet);
            }
        }

        return targetClassMap;
    }

    /**
     * 将代理目标类映射装换为增强对象映射
     */
    private static Map<Class, List<Advice>> convert2AdviceMap(Map<Advice, Set<Class>> targetClassMap) {
        Map<Class, List<Advice>> adviceMap = new HashMap<Class, List<Advice>>();
        for(Map.Entry<Advice, Set<Class>> entry:targetClassMap.entrySet()) {
            Advice adviceObject = entry.getKey();
            Set<Class> targetClassSet = entry.getValue();
            for(Class targetClass:targetClassSet) {
                if(!adviceMap.containsKey(targetClass)) {
                    List<Advice> adviceList = new ArrayList<Advice>();
                    adviceList.add(adviceObject);
                    adviceMap.put(targetClass, adviceList);
                }
                else {
                    adviceMap.get(targetClass).add(adviceObject);
                }
            }
        }

        return adviceMap;
    }

    /**
     * 得到代理bean 映射
     */
    private static Map<Class, Object> getProxyMap(Map<Class, List<Advice>> adviceMap) {
        Map<Class, Object> proxyMap = new HashMap<Class, Object>();
        for(Map.Entry<Class, List<Advice>> entry:adviceMap.entrySet()) {
            Class targetClass = entry.getKey();
            List<Advice> adviceList = entry.getValue();
            Object proxy = ProxyManager.createProxy(targetClass, adviceList);

            proxyMap.put(targetClass, proxy);
        }

        return proxyMap;
    }
}
