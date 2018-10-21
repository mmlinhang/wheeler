package lh.wheel.aop.test;

import lh.wheel.aop.advice.DefaultAdvice;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TestAdvice extends DefaultAdvice {
    @Override
    public void begin(Object targetObject, Method method, MethodProxy methodProxy, Object... params) throws Throwable {
        System.out.println("begin");
    }

    @Override
    public void end(Object targetObject, Method method, MethodProxy methodProxy, Object... params) throws Throwable {
        System.out.println("end");
    }
}
