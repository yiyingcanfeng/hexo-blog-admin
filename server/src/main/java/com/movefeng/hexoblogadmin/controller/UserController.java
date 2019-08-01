package com.movefeng.hexoblogadmin.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movefeng.hexoblogadmin.model.User;
import com.movefeng.hexoblogadmin.service.AdminService;
import com.movefeng.hexoblogadmin.service.UserService;
import com.movefeng.hexoblogadmin.vo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author z
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @Resource
    private UserService userService;

    /**
     * 查询所有用户
     *
     * @return
     */
    @RequestMapping("list")
    public Result list(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestBody(required = false) Map<String, Object> searchParam) {
        PageHelper.startPage(pageNum, pageSize);
        if (searchParam == null) {
            searchParam = new HashMap<>();
        }
        Page<User> page = userService.userList(searchParam);
        PageInfo<User> pageInfo = new PageInfo<>(page);
        return new Result<>(Result.Code.SUCCESS, pageInfo);
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping("delete")
    public Result delete(Integer id) {
        return userService.delete(id);
    }

    /**
     * 根据id批量删除用户
     *
     * @param map
     * @return
     */
    @RequestMapping("deleteBatch")
    public Result deleteBatch(@RequestBody Map<String, Object> map) {
        return userService.deleteBatchById(map);
    }

}
