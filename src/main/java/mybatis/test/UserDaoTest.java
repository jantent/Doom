package mybatis.test;

import mybatis.dao.UserDao;
import mybatis.entity.User;
import mybatis.entity.UserCustom;
import mybatis.entity.UserQueryVo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import util.PrintUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@SuppressWarnings("all")
public class UserDaoTest {


        //mybatis配置文件
        String resource = "mybatis/mybatis-config.xml";
        //得到配置文件输入流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建会话工厂,传入，mybatis的配置信息
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    public UserDaoTest() throws IOException {
    }

    public void testSelect() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        HashMap<String,Object> map = userDao.findUserById(2);
        PrintUtil.print(map);
    }

    public void testFindName()throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List user = userDao.findUserByName("小明");
        PrintUtil.print(user);
    }

     void testFindVo() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        // 创建包装对象，设置查询条件
        UserCustom userCustom = new UserCustom();
//        userCustom.setSex("女");
        userCustom.setUsername("小明");
        List<Integer> list = new ArrayList<>();
        list.add(6);
        list.add(7);
        list.add(8);
        UserQueryVo userQueryVo = new UserQueryVo();
        userQueryVo.setUserCustom(userCustom);
        userQueryVo.setIdList(list);
        List<UserCustom> listResult = userDao.findUserList(userQueryVo);
        PrintUtil.print(listResult);

    }

     public void testFindCount() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        // 创建包装对象，设置查询条件
        UserCustom userCustom = new UserCustom();
        userCustom.setSex("男");
        userCustom.setUsername("小明");
        UserQueryVo userQueryVo = new UserQueryVo();
        userQueryVo.setUserCustom(userCustom);

        int count = userDao.findUserCount(userQueryVo);
        PrintUtil.print(count);

    }

    public void testFindUserMap() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.findUserByResultMap(2);
        PrintUtil.print(user);

    }
}
