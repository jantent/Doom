package springBoot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springBoot.domain.bean.Admin;
import springBoot.domain.dao.AdminDao;
import springBoot.service.AdminService;
@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminDao adminDao;
    @Override
    public Admin getAdmin(int id) {
        Admin admin = adminDao.findAdminById(id);
        return admin;
    }
}
