package lh.wheel.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 字符串工具
 */
public final class StringUtils {

    private static final Logger LOGGER = Logger.getLogger(StringUtils.class);

    /**
     * 判断字符串是否为空
     * 为空条件：
     * 1. str 为 null
     * 2. str 为 空串
     * 3. str 为 "     "  (1或多个空格)
     */
    public static boolean isEmpty(String str) {
        if(str != null)
            str = str.trim();

        return org.apache.commons.lang3.StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 从 InputStream 中提取字符串
     */
    public static String getString(InputStream is) {
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = bufferedReader.readLine()) != null)
                sb.append(line);
        }
        catch (IOException e) {
            LOGGER.error("从 InputStream 中提取字符串失败", e);
            throw new RuntimeException(e);
        }
        finally {
            try {
                bufferedReader.close();
            }
            catch (IOException e) {
                LOGGER.error("流关闭失败", e);
            }
        }

        return sb.toString();
    }
}
