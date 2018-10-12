package lh.wheel.helper;

import lh.wheel.constant.ConfigKey;
import lh.wheel.util.PropsUtils;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * 配置文件加载类
 */
public class ConfigHelper {

    private static final Logger LOGGER = Logger.getLogger(ConfigHelper.class);

    /**
     * Config Values
     */
    private static final String JDBC_DRIVER;
    private static final String JDBC_URL;
    private static final String JDBC_USERNAME;
    private static final String JDBC_PASSWORD;
    private static final String BASE_PACKAGE;
    private static final String JSP_PATH;
    private static final String ASSET_PATH;

    static {
        final String CONFIG_FILE = "wheel.properties";
        final Properties CONFIG_PROPS = PropsUtils.loadProps(CONFIG_FILE);

        /**
         * 以下配置有默认值 ""
         */
        JDBC_DRIVER = PropsUtils.getString(CONFIG_PROPS, ConfigKey.JDBC_DRIVER, "");
        JDBC_URL = PropsUtils.getString(CONFIG_PROPS, ConfigKey.JDBC_URL, "");
        JDBC_USERNAME = PropsUtils.getString(CONFIG_PROPS, ConfigKey.JDBC_USERNAME, "");
        JDBC_PASSWORD = PropsUtils.getString(CONFIG_PROPS, ConfigKey.JDBC_PASSWORD, "");

        /**
         * 以下配置若配置文件未给出，则报错
         */
        BASE_PACKAGE = parseReqConfig(CONFIG_PROPS, ConfigKey.BASE_PACKAGE, "lh.wheel.app.base_package 配置项不能为空");
        JSP_PATH = parseReqConfig(CONFIG_PROPS, ConfigKey.JSP_PATH, "lh.wheel.app.jsp_path 配置项不能为空");
        ASSET_PATH = parseReqConfig(CONFIG_PROPS, ConfigKey.ASSET_PATH, "lh.wheel.app.asset_path 配置项不能为空");
    }

    public static String getJdbcDriver() {
        return JDBC_DRIVER;
    }

    public static String getJdbcUrl() {
        return JDBC_URL;
    }

    public static String getJdbcUsername() {
        return JDBC_USERNAME;
    }

    public static String getJdbcPassword() {
        return JDBC_PASSWORD;
    }

    public static String getBasePackage() {
        return BASE_PACKAGE;
    }

    public static String getJspPath() {
        return JSP_PATH;
    }

    public static String getAssetPath() {
        return ASSET_PATH;
    }

    private static String parseReqConfig(Properties configProps, String configKey, String errorMess) {
        String configValue = PropsUtils.getString(configProps, configKey);
        if(configValue == null) {
            LOGGER.error(errorMess);
            throw new RuntimeException(errorMess);
        }

        return configValue;
    }
}
