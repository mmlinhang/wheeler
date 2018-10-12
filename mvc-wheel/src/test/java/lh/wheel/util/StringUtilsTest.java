package lh.wheel.util;

import org.apache.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * StringUtils 测试
 */
public class StringUtilsTest {

    private final Logger LOGGER = Logger.getLogger(StringUtilsTest.class);

    @Test
    public void testIsEmpty() {
        //null | "" | "   "
        assertEquals(StringUtils.isEmpty(null), true);
        assertEquals(StringUtils.isEmpty(""), true);
        assertEquals(StringUtils.isEmpty("    "), true);

        // string
        assertEquals(StringUtils.isEmpty("'"), false);
    }
}
