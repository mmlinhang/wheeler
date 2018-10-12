package lh.wheel.helper;

import lh.wheel.annotationTest.ControllerExam;
import lh.wheel.annotationTest.ServiceExam;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Map;
import static org.junit.Assert.*;
public class BeanHelperTest {
    private final Logger LOGGER = Logger.getLogger(BeanHelperTest.class);

    @Test
    public void testBeanLoader() {
        Map<Class, Object> controllerMap = BeanHelper.getControllerMap();
        Map<Class, Object> serviceMap = BeanHelper.getServiceMap();

        assertTrue(controllerMap.get(ControllerExam.class) instanceof ControllerExam);
        assertTrue(serviceMap.get(ServiceExam.class) instanceof ServiceExam);
        assertEquals(controllerMap.size(), 1);
        assertEquals(serviceMap.size(), 1);
    }
}
