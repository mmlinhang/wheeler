package lh.wheel.thread;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现简易版 ThreadLocal
 */
public class MyThreadLocal<T> {
    private final Map<Thread, T> threadValues = Collections.synchronizedMap(new HashMap<Thread, T>());

    /**
     * 线程变量的初始值
     * 若没有被子类重写，则初始值为 null
     */
    protected T initValue() {
        return null;
    }

    public T get() {
        Thread currentThread = Thread.currentThread();
        if(threadValues.containsKey(currentThread))
            return threadValues.get(currentThread);
        else {
            T initValue = initValue();
            threadValues.put(currentThread, initValue);
            return initValue;
        }
    }

    public void set(T value) {
        threadValues.put(Thread.currentThread(), value);
    }

    public void remove() {
        threadValues.remove(Thread.currentThread());
    }
}
