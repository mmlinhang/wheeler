package lh.wheel.thread;

import org.apache.log4j.Logger;
import org.junit.Test;

public class MyThreadLocalTest {
    private final Logger LOGGER = Logger.getLogger(MyThreadLocalTest.class);

    private MyThreadLocal<Integer> countContainer = new MyThreadLocal<Integer>() {
        @Override
        protected Integer initValue() {
            return 0;
        }
    };

    @Test
    public void testMyThreadLocal() {
        for(int i = 0; i < 5; i++) {
            Thread thread = new MyThread();
            thread.start();
        }

        try {
            Thread.sleep(1000*60);
        }
        catch (Exception e) {

        }

    }

    private class MyThread extends Thread{
        @Override
        public void run() {
            for(int i = 0; i < 100; i++) {
                int count = countContainer.get();
                System.out.println("Thread: "+Thread.currentThread()+"  [count: "+count+"]");
                if(count==50)
                    countContainer.remove();
                else
                    countContainer.set(count+1);
            }
        }
    }
}
