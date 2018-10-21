package lh.wheel.aop;

import lh.wheel.aop.advice.Advice;
import lh.wheel.aop.proxy.ProxyManager;
import lh.wheel.aop.test.TestAdvice;
import lh.wheel.aop.test.TestClass;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProxyManagerTest {
    private final Logger LOGGER = Logger.getLogger(ProxyManagerTest.class);

    @Test
    public void testCreateProxy() {
        List<Advice> adviceList = new ArrayList<Advice>();
        adviceList.add(new TestAdvice());
        TestClass testClass = ProxyManager.createProxy(TestClass.class, adviceList);
        testClass.hello();
    }
}
