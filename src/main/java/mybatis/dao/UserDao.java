package mybatis.dao;

import mybatis.entity.User;
import mybatis.entity.UserCustom;
import mybatis.entity.UserQueryVo;

import java.util.HashMap;
import java.util.List;

public interface UserDao {
    /**
     * mapper代理开发规范
     * 1.在mapper.xml中namespace等于接口地址 比如 这个类就是填写mybatis.dao.UserDao
     * 2.Dao.java接口中的方法名与mapper.xml中的statement的id相等
     * 3.Dao.java接口中的方法输入类型和mapper.xml中的statement的paramType指定的类型一致
     * 4.Dao.java接口中的方法返回类型与mapper.xml中的statement的resultType指定
     */

    /**
     * resultType
     *使用resultType进行输出映射，只有查询出来的和pojo中的属性名一致，该列才可以成功映射
     * 如果查询出来的列名和pojo中的属性名全部不一致，没有创建pojo对象
     * 只要查询出来的列名和pojo中的属性有一个一致，就会创建pojo对象
     *
     * 查询出来的结果只有一行且一列，可以使用简单类型进行输出
     *
     * 不管是輸出的pojo单个独享还是一个列表（list中包括pojo),在mapper.xml中resultType制定的类型是一样的
     * 在mapper.java指定的方法返回值类型不一样：
     *      1.输出单个pojo对象，方法返回值是单个对象类型
     *      2.输出pojo对象List,方法返回值是List<Pojo>
     * 生成的动态代理对象中是根据mapper方法的返回值类型确定是调用selectone还是selectList
     */


    /**
     * resultMap
     * mybatis中适应resultMap完成改机输出结果映射。
     * 如果查询出来的列名和pojo的属性名不一致，通过定义一个resultMap对列名和pojo属性名之间做一个映射关系。
     * 1.定义resultMap
     *
     * 2.使用resultMap作为statement的输出映射类型
     *
     */

    public List<UserCustom> findUserList(UserQueryVo userQueryVo) throws Exception;

    public User findUserByResultMap(int id) throws Exception;

    /**
     * 使用hashMap返回对象
     * @param id
     * @return
     * @throws Exception
     */
    public HashMap<String,Object> findUserById(int id) throws Exception;

    public List<User> findUserByName(String name) throws Exception;

    public int findUserCount(UserQueryVo userQueryVo) throws Exception;

    public void insertUser(User user) throws Exception;

    public int insertUserReturnId(User user) throws Exception;

    public void deleteUser(int id) throws Exception;

    public void updateUser(User user) throws Exception;
}
