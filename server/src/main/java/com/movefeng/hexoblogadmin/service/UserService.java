package com.movefeng.hexoblogadmin.service;

import com.github.pagehelper.Page;
import com.movefeng.hexoblogadmin.dao.UserDao;
import com.movefeng.hexoblogadmin.model.User;
import com.movefeng.hexoblogadmin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author z
 */
@Service
@Slf4j
public class UserService {

    @Resource
    private UserDao userDao;

    /**
     * 查询所有用户
     *
     * @return
     */
    public Page<User> userList(Map<String, Object> param) {
        return userDao.selectUserAll(param);
    }
}
