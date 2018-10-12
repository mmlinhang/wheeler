package lh.wheel.helper;

import lh.wheel.annotationTest.ControllerExam;
import org.apache.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Map;

public class InjectHelperTest {
    private final Logger LOGGER = Logger.getLogger(InjectHelperTest.class);

    @Test
    public void testInject() {
        Map<Class, Object> controllerMap = BeanHelper.getControllerMap();
        Map<Class, Object> serviceMap = BeanHelper.getServiceMap();
        try {
            Class.forName("lh.wheel.helper.InjectHelper");
        }
        catch (ClassNotFoundException e) {
            System.out.println("类加载失败");
        }

        //正常测试
        ControllerExam controllerExam = (ControllerExam) controllerMap.get(ControllerExam.class);
    }
}
