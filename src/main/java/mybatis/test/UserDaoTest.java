package mybatis.test;

import mybatis.dao.UserDao;
import mybatis.eneity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import util.PrintUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserDaoTest {


        //mybatis配置文件
        String resource = "mybatis/mybatis-config.xml";
        //得到配置文件输入流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建会话工厂,传入，mybatis的配置信息
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    public UserDaoTest() throws IOException {
    }

    @Test
    public void testSelect() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        PrintUtil.print(userDao.findUserById(2));
    }

    @Test
    public void testFindName()throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List user = userDao.findUserByName("小明");
        PrintUtil.print(user);
    }
}
