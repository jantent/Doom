package jdbc.pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcPoolUtil {

	private static JdbcPool pool = new JdbcPool();

	public static Connection getConnection() throws SQLException{
		return pool.getConnection();
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
