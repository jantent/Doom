package jdbc;

import java.sql.*;

public class JdbcDemo {

	public static void main(String args[]) throws ClassNotFoundException, SQLException{
		//数据库地址
		String url = "jdbc:mysql://localhost:3306/jdbc";
		//用户名
		String usename = "root";
		//密码
		String password = "123456";

		//1.加载驱动
		Class.forName("com.mysql.jdbc.Driver");
		//2.获取与数据库的链接
		Connection connection = DriverManager.getConnection(url, usename, password);
		//3.获取加载数据库的statement
		Statement statement = connection.createStatement();
		String sql = "select * from user";
		//4.向数据库发sql,获取代表结果集的resultset
		ResultSet resultSet = statement.executeQuery(sql);
		//5.从结果集中取出数据
		while(resultSet.next()){
			System.out.println("id = "+resultSet.getObject("ID"));
			System.out.println("name = "+resultSet.getObject("name"));
			System.out.println("pwd= "+resultSet.getObject("pwd"));
		}
		//6.关闭连接释放资源
		resultSet.close();
		statement.close();
		connection.close();
	}
}
