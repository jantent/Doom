package jdbc.crud;

import jdbc.util.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcCurdByStatement {

	private static Connection conn = null;
	private static Statement st = null;
	private static ResultSet rs = null;

	public static Connection getConn() {
		return conn;
	}

	public static void setConn(Connection conn) {
		JdbcCurdByStatement.conn = conn;
	}

	public static Statement getSt() {
		return st;
	}

	public static void setSt(Statement st) {
		JdbcCurdByStatement.st = st;
	}

	public static ResultSet getRs() {
		return rs;
	}

	public static void setRs(ResultSet rs) {
		JdbcCurdByStatement.rs = rs;
	}

	public static void insert(String value){
		try {
			//获取一个数据库连接
			conn = JdbcUtil.getConnection();
			//通过conn对象获取负责执行SQL命令的Statement对象
			st = conn.createStatement();
			//要执行的sql
			String sql = "insert into user(ID,name,pwd) values(6,'test','456')";
			//执行插入操作
			int num = st.executeUpdate(sql);
			if(num>0){
				System.out.println("插入成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static void main(String args[]){
		JdbcCurdByStatement.insert("hello");
	}
}
