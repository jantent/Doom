package mybatis.dao;

import mybatis.eneity.User;

public interface UserDao {
    /**
     * mapper代理开发规范
     * 1.在mapper.xml中namespace等于接口地址 比如 这个类就是填写mybatis.dao.UserDao
     * 2.Dao.java接口中的方法名与mapper.xml中的statement的id相等
     * 3.Dao.java接口中的方法输入类型和mapper.xml中的statement的paramType指定的类型一致
     * 4.Dao.java接口中的方法返回类型与mapper.xml中的statement的resultType指定
     */

    public User findUserById(int id) throws Exception;

    public void insertUser(User user) throws Exception;

    public int insertUserReturnId(User user) throws Exception;

    public void deleteUser(int id) throws Exception;

    public void updateUser(User user) throws Exception;
}
