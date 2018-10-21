package lh.wheel.util;

import lh.wheel.entity.Student;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JsonUtilsTest {
    private final Logger logger = Logger.getLogger(JsonUtilsTest.class);

    @Test
    public void testToJson() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "v");
        map.put("2", "c");
        String json = JsonUtils.toJson(map);
        System.out.println(json);
    }
}
