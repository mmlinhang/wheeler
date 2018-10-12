package lh.wheel.util;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Set;

public class ClassUtilsTest {
    private final Logger logger = Logger.getLogger(ClassUtilsTest.class);

    @Test
    public void testGetClassSet() {
        Set<Class> classSet = ClassUtils.getClassSet("lh/wheel/classUtilsTest");

        for(Class clazz:classSet) {
            System.out.println(clazz.getSimpleName());
        }
    }
}
