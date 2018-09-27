package lh.wheeler.util;

import org.apache.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Properties;

/**
 * PropsUtils 测试
 */
public class PropsUtilsTest {

    private final Logger LOGGER = Logger.getLogger(PropsUtilsTest.class);

    @Test
    public void testLoadProps() {
        String fileName;
        Properties props;

        //配置文件不存在测试
        fileName = "notExists.properties";
        props = PropsUtils.loadProps(fileName);
        assertNull(props);

        //正常情况测试
        fileName = "testProps.properties";
        props = PropsUtils.loadProps(fileName);
        assertEquals(PropsUtils.getString(props, "jdbc.driver"), "com.mysql");

        //key 不存在测试
        assertNull(PropsUtils.getString(props, "jdbc.notExists"));
    }
}
