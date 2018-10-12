package lh.wheel.util;

import org.apache.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * CastUtils 测试
 */
public class CastUtilsTest {
    private final Logger LOGGER = Logger.getLogger(CastUtilsTest.class);

    @Test
    public void testCastString() {
        //null
        assertEquals(CastUtils.castString(null), "");
        assertEquals(CastUtils.castString(null, "null"), "null");
        //基本类型
        assertEquals(CastUtils.castString(false), "false");
        assertEquals(CastUtils.castString(1000), "1000");
        //对象
        LOGGER.info(CastUtils.castString(new Object()));
    }

    @Test
    public void testCastInt() {
        //null
        assertEquals(CastUtils.castInt(null), 0);
        //异常
        assertEquals(CastUtils.castInt(""), 0);
        assertEquals(CastUtils.castInt(" "), 0);
        assertEquals(CastUtils.castInt("abcd"), 0);
        assertEquals(CastUtils.castInt("异常"), 0);
        //正常
        assertEquals(CastUtils.castInt("1234"), 1234);
    }
}
