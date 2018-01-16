package springBoot.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import springBoot.domain.bean.Admin;
@Component
@Mapper
public interface AdminDao {
    public Admin findAdminById(int id);

    public int insertAdminReturnId(Admin admin);
}
