package com.movefeng.hexoblogadmin.service;

import com.movefeng.hexoblogadmin.dao.AdminDao;
import com.movefeng.hexoblogadmin.model.Admin;
import com.movefeng.hexoblogadmin.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author z
 */
@Service
@Slf4j
public class AdminService {

    @Resource
    private AdminDao adminDao;

    public boolean adminLogin(Admin admin) {
        String password = admin.getPassword();
        admin.setPassword(AESUtil.encryptBase64(password, adminInfo(admin).getSecret()));

        Admin admin1 = adminDao.selectAdminByUsernameAndPassword(admin);
        return admin1 != null;

    }

    public Admin adminInfo(Admin admin) {
        return adminDao.selectAdminByUsername(admin);
    }

}
