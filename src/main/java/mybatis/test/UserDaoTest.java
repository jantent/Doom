package mybatis.test;

import mybatis.dao.UserDao;
import mybatis.eneity.User;
import mybatis.eneity.UserCustom;
import mybatis.eneity.UserQueryVo;
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

    @Test void testFindVo() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        // 创建包装对象，设置查询条件
        UserCustom userCustom = new UserCustom();
        userCustom.setSex("女");
        userCustom.setUsername("小明");
        UserQueryVo userQueryVo = new UserQueryVo();
        userQueryVo.setUserCustom(userCustom);

        List<UserCustom> list = userDao.findUserList(userQueryVo);
        PrintUtil.print(list);

    }
}
