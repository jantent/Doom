package mybatis.execute;


import mybatis.dao.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SuppressWarnings("all")
public class MybatisSelectDemo {
    @Test
    public void  findUserByIdTest() throws IOException {
        //mybatis配置文件
        String resource = "mybatis/mybatis-config.xml";
        //得到配置文件输入流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建会话工厂,传入，mybatis的配置信息
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 通过工厂获得SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 通过sqlSession操作数据库
        // 第一个参数：映射文件中statement的id，等于namespace+“.”+statement的id
        // 第二个参数：制定和映射文件中所匹配的parammeterType类型的参数
        // 结果就是映射文件中所匹配的映射的对象
        User user = sqlSession.selectOne("test.findUserById",1);
        System.out.println(user);
        sqlSession.close();
    }

    @Test
    public void findUserByName() throws Exception{
        //mybatis配置文件
        String resource = "mybatis/mybatis-config.xml";
        //得到配置文件输入流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建会话工厂,传入，mybatis的配置信息
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 通过工厂获得SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 通过sqlSession操作数据库
        // 第一个参数：映射文件中statement的id，等于namespace+“.”+statement的id
        // 第二个参数：制定和映射文件中所匹配的parammeterType类型的参数
        // 结果就是映射文件中所匹配的映射的对象
        List<User> userList = sqlSession.selectList("test.findUserByName","小明");
        System.out.println(userList);
        sqlSession.close();
    }

    /**
     * 查询总结
     * parameterType
     * 在映射文件中通过parameterType指定输入 参数的类型
     *
     * resultType
     * 在映射文件中通过resultType指定返回参数的类型
     *
     * #{}
     * 表示一个占位符
     *
     * ${}
     * 表示一个拼接符号，会引起sql注入，不建议使用
     *
     * selectone
     * 表示查询一条记录进行映射，使用selectone能实现的selectList也能实现
     *
     * selectList
     * 查询一个列表进行映射，如果使用selectList查询多条记录，不能使用selectone
     */
}
