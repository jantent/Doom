package jdbc.pool;

import javax.sql.DataSource;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

public class JdbcPool implements DataSource {

	private static LinkedList<Connection> listConnections = new LinkedList<Connection>();

	static {
		// 在静态代码块中加载数据库配置文件
		try {
			// 读取properties中的数据库配置信息
			InputStream is = new BufferedInputStream(new FileInputStream("./src/main/test/db.properties"));
			Properties prop = new Properties();
			prop.load(is);

			// 数据库驱动
			String driver = prop.getProperty("driver");
			// 数据库url
			String url = prop.getProperty("url");
			// 数据库连接用户名
			String username = prop.getProperty("username");
			// 数据库连接密码
			String password = prop.getProperty("password");
			// 数据库连接池的初始化连接数大小
			int jdbcPoolInitSize = Integer.parseInt(prop.getProperty("jdbcPoolInitSize"));
			// 加载数据库
			Class.forName(driver);
			for (int i = 0; i < jdbcPoolInitSize; i++) {
				Connection conn = DriverManager.getConnection(url, username, password);
				System.out.println("获取到了链接" + conn);
				// 将获取到的数据库连接加入到listConnections集合中，listConnections集合此时就是一个存放了数据库连接的连接池
				listConnections.add(conn);
			}
		} catch (Exception e) {
			 throw new ExceptionInInitializerError(e);
		}
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		// 如果数据库连接池中连接对象个数大于0个
		if (listConnections.size() > 0) {
			// 从集合中获取一个数据库链接
			final Connection conn = listConnections.removeFirst();
			System.out.println("数据库连接池大小是: " + listConnections.size());
			return (Connection) Proxy.newProxyInstance(JdbcPool.class.getClassLoader(), conn.getClass().getInterfaces(),
					new InvocationHandler() {

						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							if (!method.getName().equals("close")) {
								return method.invoke(conn, args);
							} else {
								// 如果调用的是Connection对象的close方法，就把conn还给数据库连接池
								listConnections.add(conn);
								System.out.println(conn + "被还给了连接池");
								System.out.println("现在连接池的大小为： " + listConnections.size());
								return null;
							}

						}
					});

		}else {
			throw new RuntimeException("对不起数据库忙");
		}
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return null;
	}

}
