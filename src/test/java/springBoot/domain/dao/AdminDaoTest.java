package springBoot.domain.dao;

import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springBoot.domain.bean.Admin;

import javax.annotation.Resource;

@MapperScan("springBoot.domain.dao")
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminDaoTest {

    @Resource
    private AdminDao adminDao;

    public void testGetUserByName() throws Exception {
        Admin admin = adminDao.findAdminById(1);
        System.out.println(admin);
    }

    public void testInsertAdminReturnId() throws Exception {
        Admin admin = new Admin();
        admin.setAddress("扬州高邮");
        admin.setAdminAccount("tangj");
        admin.setAdminPassword("123456");
        int result =  adminDao.insertAdminReturnId(admin);
        System.out.println(result);
    }

}