package springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springBoot.domain.dao.AdminDao;
import springBoot.domain.eneity.User;


@RestController
public class UserController {
    @Autowired
    private AdminDao adminDao;
    @RequestMapping("/user")
    @ResponseBody
    public String getUser()throws Exception{
        User user = adminDao.getUserByName(1);
        return user.getUsername()+"----"+user.getSex();
    }
}
