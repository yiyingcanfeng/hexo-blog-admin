package com.movefeng.hexoblogadmin.service;

import com.github.pagehelper.Page;
import com.movefeng.hexoblogadmin.dao.CommentDao;
import com.movefeng.hexoblogadmin.dao.UserDao;
import com.movefeng.hexoblogadmin.model.User;
import com.movefeng.hexoblogadmin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    private CommentDao commentDao;

    /**
     * 查询所有用户
     *
     * @return
     */
    public Page<User> userList(Map<String, Object> param) {
        return userDao.selectUserAll(param);
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Integer id) {
        Integer i = userDao.deleteById(id);
        if (i == 1) {
            commentDao.deleteByUserId(id);
            return new Result(Result.Code.SUCCESS, "删除成功！");
        } else {
            return new Result(Result.Code.ERROR, "删除失败！");
        }
    }

    /**
     * 根据id批量删除用户
     *
     * @param map
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result deleteBatchById(Map<String, List<Integer>> map) {
        if (map.get("id") != null) {
            List<Integer> idList = map.get("id");
            userDao.deleteBatchById(idList);
            commentDao.deleteBatchByUserId(idList);
            return new Result(Result.Code.SUCCESS, "操作成功！");
        } else {
            return new Result(Result.Code.ERROR, "参数不正确！");
        }
    }
}
