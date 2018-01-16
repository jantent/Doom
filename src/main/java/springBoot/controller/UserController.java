package springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springBoot.domain.dao.AdminDao;
import springBoot.domain.bean.Admin;

import javax.annotation.Resource;


@RestController
public class UserController {
    @Resource
    private AdminDao adminDao;
    @RequestMapping("/user")
    @ResponseBody
    public String getUser()throws Exception{
        Admin admin = adminDao.findAdminById(1);
        return admin.getAdminAccount()+"--- "+admin.getAddress();
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String getAdmin()throws Exception{
        Admin admin = new Admin();
        admin.setAddress("扬州");
        admin.setAdminAccount("tangj");
        admin.setAdminPassword("123456");
        int result =  adminDao.insertAdminReturnId(admin);
        return "插入结果为:"+result;
    }
}
