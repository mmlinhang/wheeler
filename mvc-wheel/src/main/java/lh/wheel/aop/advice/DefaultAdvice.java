package lh.wheel.aop.advice;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 增强 模板方法类
 */
public abstract class DefaultAdvice extends Advice {
    /**
     * 定义增强的执行逻辑
     * @param adviceChain 增强应用于某一个增强链中
     */
    @Override
    public final Object doAdvice(AdviceChain adviceChain) throws Throwable {

        Object targetObject = adviceChain.getTargetObject();
        Method method = adviceChain.getMethod();
        MethodProxy methodProxy = adviceChain.getMethodProxy();
        Object[] params = adviceChain.getParams();

        begin(targetObject, method, methodProxy, params);
        Object result = null;
        try {
            if(intercept()) {
                before(targetObject, method, methodProxy, params);
                result = adviceChain.doAdviceChain();
                after(targetObject, method, methodProxy, params);
            }
            else
                result = adviceChain.doAdviceChain();
        }
        catch (Throwable throwable) {
            error(targetObject, method, methodProxy, params);
        }
        finally {
            end(targetObject, method, methodProxy, params);
        }

        return result;
    }

    /**
     * 定义不同的切面
     * 其中：
     * 1.begin 、 end 、 error 函数抛出的异常不受处理
     * 2.error 用于处理被代理代码、before、after 的异常
     */
    public void begin(Object targetObject, Method method, MethodProxy methodProxy, Object... params) throws Throwable {

    }

    public void end(Object targetObject, Method method, MethodProxy methodProxy, Object... params) throws Throwable {

    }

    public void before(Object targetObject, Method method, MethodProxy methodProxy, Object... params) throws Throwable {

    }

    public void after(Object targetObject, Method method, MethodProxy methodProxy, Object... params) throws Throwable {

    }

    public void error(Object targetObject, Method method, MethodProxy methodProxy, Object... params) throws Throwable {

    }

    public boolean intercept() {
        return true;
    }
}
