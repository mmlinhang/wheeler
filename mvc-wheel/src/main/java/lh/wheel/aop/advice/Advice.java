package lh.wheel.aop.advice;

/**
 * 增强 基类
 */
public abstract class Advice {
    public abstract Object doAdvice(AdviceChain adviceChain) throws Throwable;
}
