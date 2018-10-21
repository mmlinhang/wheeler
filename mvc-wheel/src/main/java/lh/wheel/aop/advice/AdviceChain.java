package lh.wheel.aop.advice;

import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 增强链类
 * 用于遍历应用于被代理对象的所有增强，使增强应用于被代理对象上
 * 算法比较巧妙--重点学习
 */
public class AdviceChain {
    private Class targetClass;
    private Object targetObject;
    private Method method;
    private MethodProxy methodProxy;
    private Object[] params;

    private List<Advice> adviceList;

    private int index = 0;

    public AdviceChain(Class targetClass, Object targetObject, Method method, MethodProxy methodProxy, Object[] params, List<Advice> adviceList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.method = method;
        this.methodProxy = methodProxy;
        this.params = params;
        this.adviceList = adviceList;
    }

    public Object doAdviceChain() throws Throwable{
        Object result;
        if(index < adviceList.size()) {
            //注意：这里使用index++。而不能doAdvice之后才index++
            result = adviceList.get(index++).doAdvice(this);
        }
        else
            result = methodProxy.invokeSuper(targetObject, params);

        return result;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public MethodProxy getMethodProxy() {
        return methodProxy;
    }

    public void setMethodProxy(MethodProxy methodProxy) {
        this.methodProxy = methodProxy;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
