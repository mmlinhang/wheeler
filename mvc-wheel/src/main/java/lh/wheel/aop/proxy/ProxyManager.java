package lh.wheel.aop.proxy;

import lh.wheel.aop.advice.Advice;
import lh.wheel.aop.advice.AdviceChain;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理生成类
 */
public class ProxyManager {
    public static <T> T createProxy(final Class targetClass, final List<Advice> adviceList) {
        return (T)
        Enhancer.create(targetClass, new MethodInterceptor() {
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return new AdviceChain(targetClass, o, method, methodProxy, objects, adviceList).doAdviceChain();
            }
        });
    }
}