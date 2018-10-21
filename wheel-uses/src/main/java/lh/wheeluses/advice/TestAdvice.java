package lh.wheeluses.advice;

import lh.wheel.annotation.aop.Aspect;
import lh.wheel.annotation.mvc.Controller;
import lh.wheel.aop.advice.DefaultAdvice;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Aspect(Controller.class)
public class TestAdvice extends DefaultAdvice{
    @Override
    public void before(Object targetObject, Method method, MethodProxy methodProxy, Object... params) throws Throwable {
        System.out.println("before controller: "+targetObject.getClass());
    }

    @Override
    public void after(Object targetObject, Method method, MethodProxy methodProxy, Object... params) throws Throwable {
        System.out.println("after controller: "+targetObject.getClass());
    }
}
