package lh.wheel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static final Logger LOGGER = Logger.getLogger(JsonUtils.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * POJO 转为 JSON 字符串
     */
    public static <T> String toJson(T object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);

        }
        catch (JsonProcessingException e) {
            LOGGER.error("pojo 转为 json 出错", e);
            throw new RuntimeException(e);
        }
    }
}
