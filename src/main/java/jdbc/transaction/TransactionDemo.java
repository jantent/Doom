package jdbc.transaction;

import jdbc.util.JdbcUtil;

import java.sql.*;

/**
 * JDBC中使用事务来模似转帐
 * create table account(
 * id int primary key auto_increment,
 * name varchar(40),
 * money float
 * );
 * insert into account(name,money) values('A',1000);
 * insert into account(name,money) values('B',1000);
 * insert into account(name,money) values('C',1000);
 *
 * @author tangj
 */
public class TransactionDemo {

    static Connection conn = null;
    static PreparedStatement st = null;
    static ResultSet rs = null;
    static Savepoint sp = null;

    public static void payAToB() {
        try {
            conn = JdbcUtil.getConnection();
            //开启数据库事务
            conn.setAutoCommit(false);
            String sql = "update account set money=money-100 where name ='A'";
            st = conn.prepareStatement(sql);
            st.executeUpdate();
            String sql2 = "update account set money=money+100 where name ='B'";
            st = conn.prepareStatement(sql2);
            st.executeUpdate();
            //上面的两条SQL执行Update语句成功之后就通知数据库提交事务(commit)
            conn.commit();
            System.out.println("执行成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn, st, rs);
        }
    }

    /**
     * 模拟转账过程中出现异常导致有一部分SQL执行失败时手动通知数据库回滚事务
     */
    public static void rollBackE() {
        try {
            conn = JdbcUtil.getConnection();
            //开启数据库事务
            conn.setAutoCommit(false);
            String sql = "update account set money=money-100 where name ='A'";
            st = conn.prepareStatement(sql);
            st.executeUpdate();
            int x = 1 / 0;
            String sql2 = "update account set money=money+100 where name ='B'";
            st = conn.prepareStatement(sql2);
            st.executeUpdate();
            //上面的两条SQL执行Update语句成功之后就通知数据库提交事务(commit)
            conn.commit();
            System.out.println("执行成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn, st, rs);
        }
    }

    /**
     * 出现异常数据回滚
     */
    public static void rollBack() {
        try {
            conn = JdbcUtil.getConnection();
            //开启数据库事务
            conn.setAutoCommit(false);
            String sql = "update account set money=money-100 where name ='A'";
            st = conn.prepareStatement(sql);
            st.executeUpdate();
            int x = 1 / 0;
            String sql2 = "update account set money=money+100 where name ='B'";
            st = conn.prepareStatement(sql2);
            st.executeUpdate();
            //上面的两条SQL执行Update语句成功之后就通知数据库提交事务(commit)
            conn.commit();
            System.out.println("执行成功");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
                System.out.println("数据已回滚");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            JdbcUtil.release(conn, st, rs);
        }
    }

    public static void rollBackPoint() {
        try {
            conn = JdbcUtil.getConnection();
            //开启数据库事务
            conn.setAutoCommit(false);
            String sql = "update account set money=money-100 where name ='A'";
            st = conn.prepareStatement(sql);
            st.executeUpdate();

            //设置事物回滚点
            sp = conn.setSavepoint();

            String sql2 = "update account set money=money+100 where name ='B'";
            st = conn.prepareStatement(sql2);
            st.executeUpdate();

            //异常开始
            int x = 1 / 0;
            String sql3 = "update account set money = money+100 where nam='C'";
            st = conn.prepareStatement(sql3);
            st.executeUpdate();

            conn.commit();
            System.out.println("执行成功");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                /**
                 * 总共有三条sql语句，异常是在sql2和sql3之间的，
                 * 回滚点设置在第一条执行之后
                 *
                 */
                conn.rollback(sp);
                conn.commit();
                System.out.println("数据已回滚");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            JdbcUtil.release(conn, st, rs);
        }
    }

    public static void main(String args[]) {
        //TransactionDemo.payAToB();
//		TransactionDemo.rollBack();
        TransactionDemo.rollBackPoint();
    }

}
