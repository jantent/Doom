package jdbc.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * JDBC工具类
 * @author tangj
 *
 */
public class JdbcUtil {

	private static String driver = null;
	private static String url = null;
	private static String username = null;
	private static String password = null;

	static {
		try {
			// 读取properties中的数据库配置信息
			InputStream is =new BufferedInputStream(new FileInputStream("./src/main/test/db.properties"));
			Properties prop = new Properties();
			prop.load(is);

			// 数据库驱动
			driver = prop.getProperty("driver");
			// 数据库url
			url = prop.getProperty("url");
			// 数据库连接用户名
			username = prop.getProperty("username");
			// 数据库连接密码
			password = prop.getProperty("password");

			// 加载数据库
			Class.forName(driver);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 获取数据库连接对象
	 *
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public static void release(Connection connection, Statement state, ResultSet resultSet) {
		// 关闭存储查询结果的ResultSet对象
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 关闭负责执行SQL命令的Statement对象
		if (state != null) {
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 关闭Connection数据库连接对象
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
