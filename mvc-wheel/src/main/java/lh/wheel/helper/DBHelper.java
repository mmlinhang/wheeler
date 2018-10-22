package lh.wheel.helper;

import lh.wheel.util.PropsUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;
import org.apache.commons.dbutils.QueryRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库操作封装类
 * 1. 封装数据源， 一个线程一个connection
 * 2. 封装查询操作：查询实体、查询实体列表、通用查询操作
 * 3. 封装通用更新操作
 */
public final class DBHelper {
    private final static Logger LOGGER = Logger.getLogger(DBHelper.class);
    private final static ThreadLocal<Connection> COON_HOLDER = new ThreadLocal<Connection>();
    private final static BasicDataSource DATA_SOURCE;
    private final static QueryRunner QUERY_RUNNER = new QueryRunner();

    /**
     * 初始化数据源
     */
    static {
        Properties props = PropsUtils.loadProps("jdbc.properties");
        final String DRIVER = PropsUtils.getString(props, "jdbc.driver");
        final String URL = PropsUtils.getString(props, "jdbc.url");
        final String USER_NAME = PropsUtils.getString(props, "jdbc.username");
        final String PASSWORD = PropsUtils.getString(props, "jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USER_NAME);
        DATA_SOURCE.setPassword(PASSWORD);
    }

    /**
     * 得到 connection
     */
    public static Connection getConnection() {
        Connection coon = COON_HOLDER.get();
        if(coon == null) {
            try {
                coon = DATA_SOURCE.getConnection();
                COON_HOLDER.set(coon);
            }
            catch(SQLException e) {
                LOGGER.error("获取 connection 失败");
                throw new RuntimeException(e);
            }
        }

        return coon;
    }

    /**
     * 关闭 connection
     */
    public static void closeConnection() {
        Connection coon = COON_HOLDER.get();
        if(coon != null) {
            try {
                coon.close();
                COON_HOLDER.remove();
            }
            catch (SQLException e) {
                LOGGER.error("关闭 connection 失败");
            }
        }
    }

    /**
     * 查询实体
     */
    public static <T> T queryEntity(String sql, Class<T> entityClass, Object... params) {
        T t;
        try {
            t = QUERY_RUNNER.query(getConnection(), sql, new BeanHandler<T>(entityClass), params);
        }
        catch(SQLException e) {
            LOGGER.error(sql+" 查询实体失败");
            throw new RuntimeException(e);
        }

        return t;
    }

    /**
     * 查询实体列表
     */
    public static <T> List<T> queryEntityList(String sql, Class<T> entityClass, Object... params) {
        List<T> ts;
        try {
            ts = QUERY_RUNNER.query(getConnection(), sql, new BeanListHandler<T>(entityClass), params);
        }
        catch (SQLException e) {
            LOGGER.error(sql+" 查询实体列表失败");
            throw new RuntimeException(e);
        }

        return ts;
    }

    /**
     * 通用查询操作 (可用于多表查询)
     */
    public static List<Map<String, Object>> query(String sql, Object... params) {
        List<Map<String, Object>> result;
        try {
            result = QUERY_RUNNER.query(sql, new MapListHandler(), params);
        }
        catch(SQLException e) {
            LOGGER.error(sql+" 查询失败");
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 通用更新操作
     */
    public static int executeUpdate(String sql, Object... params) {
        int rows;
        try {
            rows = QUERY_RUNNER.execute(getConnection(), sql, params);
        }
        catch(SQLException e) {
            LOGGER.error(sql+" 执行更新错误");
            throw new RuntimeException(e);
        }

        return rows;
    }

    /**
     * 插入实体
     */
    public static <T> boolean insert(Map<String, Object> row, Class<T> tClass) {
        String sql = "INSERT INTO " + tClass.getSimpleName();
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for(String key:row.keySet()) {
            columns.append(key).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + " VALUES " + values;

        Object[] params = row.values().toArray();

        return executeUpdate(sql, params) == 1;
    }

    /**
     * 更新实体
     */
    public static <T> boolean update(Map<String, Object> row, int id, Class<T> tClass) {
        String sql = "UPDATE " + tClass.getSimpleName() + " SET ";
        for(String key:row.keySet())
            sql = sql + key + "=" + row.get(key) + ", ";
        sql = sql.substring(0, sql.lastIndexOf(",")) + " WHERE id=" + id;

        return executeUpdate(sql) == 1;
    }

    /**
     * 删除实体
     */
    public static <T> boolean delete(int id, Class<T> tClass) {
        String sql = "DELETE FROM " + tClass.getSimpleName() + " WHERE id=" + id;
        return executeUpdate(sql) == 1;
    }

    /**
     * 执行sql脚本
     */
    public static void executeSqlFile(String fileName) {
        InputStream is = null;
        BufferedReader reader = null;
        try{
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(is == null) {
                LOGGER.error(fileName+" sql脚本未找到");
                throw new FileNotFoundException(fileName+" sql脚本未找到");
            }
            reader = new BufferedReader(new InputStreamReader(is));

            String sql;
            while((sql=reader.readLine()) != null)
                executeUpdate(sql);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally{
            try{
                if(is != null)
                    is.close();
                if(reader != null)
                    reader.close();
            }
            catch (IOException e) {
                LOGGER.error("文件流关闭失败");
            }
        }
    }

    /**
     * 开启事务
     */
    public static void startTransaction() {
        Connection conn = getConnection();
        try {
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            LOGGER.error("开启事务失败");
            throw new RuntimeException(e);
        }
        finally {
            COON_HOLDER.set(conn);
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        try {
            conn.commit();
            conn.close();
        }
        catch (SQLException e) {
            LOGGER.error("事务提交失败");
            throw new RuntimeException(e);
        }
        finally {
            COON_HOLDER.remove();
        }
    }

    /**
     * 回滚事务
     */
    public static void rollBackTransaction() {
        Connection conn = getConnection();
        try {
            conn.rollback();
            conn.close();
        }
        catch (SQLException e) {
            LOGGER.error("事务回滚失败");
            throw new RuntimeException(e);
        }
        finally {
            COON_HOLDER.remove();
        }
    }
}
