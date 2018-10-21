package lh.wheel.helper;


import lh.wheel.constant.RequestMethod;
import org.apache.log4j.Logger;
import org.junit.Test;
import static  org.junit.Assert.*;

import java.lang.reflect.Method;

public class ActionHelperTest {
    private final Logger LOGGER = Logger.getLogger(ActionHelperTest.class);

    @Test
    public void testGetServiceMethod() {
       Method method = ActionHelper.getMappedServiceMethod(RequestMethod.GET, "listStudents");
       assertEquals(method.getName(), "listStudents");
    }
}
