package springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springBoot.domain.dao.AdminDao;
import springBoot.domain.bean.Admin;
import springBoot.service.AdminService;

import javax.annotation.Resource;


@RestController
public class UserController {

    @Autowired
    private AdminService adminService;
    @RequestMapping("/user")
    @ResponseBody
    public String getUser()throws Exception{
        Admin admin = adminService.getAdmin(2);
        return admin.getAdminAccount()+"--- "+admin.getAddress();
    }


}
