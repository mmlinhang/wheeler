package lh.wheel.constant;

public interface ConfigKey {

    /**
     * jdbc
     */
    String JDBC_DRIVER = "lh.wheel.jdbc.driver";
    String JDBC_URL = "lh.wheel.jdbc.url";
    String JDBC_USERNAME = "lh.wheel.jdbc.username";
    String JDBC_PASSWORD = "lh.wheel.jdbc.password";

    /**
     * app
     */
    //框架扫描的基包
    String BASE_PACKAGE = "lh.wheel.app.base_package";

    //jsp文件的根路径
    String JSP_PATH = "lh.wheel.app.jsp_path";

    //静态文件的根路径
    String ASSET_PATH = "lh.wheel.app.asset_path";
}
