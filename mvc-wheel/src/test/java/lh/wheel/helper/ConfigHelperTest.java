package lh.wheel.helper;

import org.apache.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigHelperTest {
    private final Logger LOGGER = Logger.getLogger(ConfigHelperTest.class);

    @Test
    public void testGetConfig() {
        //正常情况
        assertEquals(ConfigHelper.getBasePackage(), "lh.wheel");
        assertEquals(ConfigHelper.getJspPath(), "WEB_INF/");
        assertEquals(ConfigHelper.getJdbcDriver(), "org.mysql");
        assertEquals(ConfigHelper.getJdbcUrl(), "");
        assertEquals(ConfigHelper.getAssetPath(), "");
    }
}
